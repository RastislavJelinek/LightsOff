package sk.tuke.gamestudio.lightsOff.consoleUi;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.lightsOff.core.Field;
import sk.tuke.gamestudio.lightsOff.core.GameState;
import sk.tuke.gamestudio.lightsOff.core.Tile;
import sk.tuke.gamestudio.lightsOff.core.TileState;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.RatingServiceJDBC;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {

    Field field;
    private int counter = 0;

    @Autowired
    private ScoreService scoreService;


    public ConsoleUI(Field field, ScoreService scoreService) {
        this.field = field;
        this.scoreService = scoreService;
    }

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void play() {
        Scanner myInput = new Scanner( System.in );
        do {
            field.nextLevel();
            field.generate();
            do {
                show();
                handleInput();
            } while (field.getState() == GameState.PLAYING);

            show();
            System.out.println("Solved!");
            System.out.print("want to play next level?(y/n):");
        }while(myInput.next().equals("y"));
        System.out.print("want to leave? (y/n):");
        if(myInput.next().equals("y"))return;

        System.out.print("your name?:");
        String name = myInput.next();
        field.nextLevel();
        ScoreServiceJDBC scoreServiceJDBC = new ScoreServiceJDBC();

        scoreServiceJDBC.addScore(new Score("lightsOff",name,field.getScore(),new Date()));
        ColorPrint.println("congratulation " + name + "! your score is: " + field.getScore(),ColorPrint.ANSI_PURPLE);



        System.out.print("want to see TOP 10 score? / no (y/n):");
        if(myInput.next().equals("y")){
            scoreServiceJDBC.getTopScores("lightsOff").forEach(t ->ColorPrint.println(t.toString(),ColorPrint.ANSI_PURPLE));
        }

        commentAction(myInput, name);
        ratingAction(myInput, name);

        ColorPrint.println("Wish you a nice day :D see you soon",ColorPrint.ANSI_YELLOW);

    }

    private void ratingAction(Scanner myInput, String name) {
        String ratingActionChooser;
        do {
        System.out.print("want to leave rating? / see average rating? / see your previous rating? / no (l/a/p/n):");
        ratingActionChooser = myInput.next();
        RatingServiceJDBC ratingServiceJDBC = new RatingServiceJDBC();
        switch (ratingActionChooser) {
            case "l" -> {
                do
                    try {
                        System.out.print("give rating(1-5): ");
                        ratingServiceJDBC.setRating(new Rating("lightsOff", name, myInput.nextInt(), new Date()));
                        break;
                    } catch (OutOfRangeException e) {
                        System.err.println("rating not in range 1 - 5");
                    }
                while(true);
            }
            case "a" -> ColorPrint.println("Average rating of this game: "+ ratingServiceJDBC.getAverageRating("lightsOff"),ColorPrint.ANSI_PURPLE);
            case "p" -> ColorPrint.println("Your previous rating: "+ ratingServiceJDBC.getRating("lightsOff", name),ColorPrint.ANSI_PURPLE);
        }
        }while(ratingActionChooser.equals("l") || ratingActionChooser.equals("a") || ratingActionChooser.equals("p"));
    }

    private void commentAction(Scanner myInput, String name) {
        String commentActionChooser;
        do {
            System.out.print("want to leave comment? / see most recent comments? / no (l/s/n):");
            commentActionChooser = myInput.next();
            CommentServiceJDBC commentServiceJDBC = new CommentServiceJDBC();
            switch (commentActionChooser) {
                case "l" -> {
                    String comment;
                    do {
                        System.out.print("your comment(must be less then 300 characters): ");
                        comment = myInput.next();
                    } while (comment.length() > 300);
                    commentServiceJDBC.addComment(new Comment("lightsOff", name, comment, new Date()));
                }
                case "s" -> commentServiceJDBC.getComments("lightsOff").forEach(t -> ColorPrint.println(t.toString(), ColorPrint.ANSI_PURPLE));
            }
        }while(commentActionChooser.equals("l") || commentActionChooser.equals("s"));
    }

    private void handleInput() {
        Scanner myInput = new Scanner( System.in );
        System.out.print("Type row coordinate:");
        int row = myInput.nextInt();
        System.out.print("Type column coordinate:");
        int column = myInput.nextInt();
        field.switchTile(row,column);
    }

    private void show(){
        ColorPrint.println("level: " + field.getLevel(),ColorPrint.ANSI_BLUE);
        ColorPrint.println("score: " + field.getScore(),ColorPrint.ANSI_BLUE);

        System.out.print("  ");
        for(int i = 0; i < field.getColumnCount(); ++i){
            ColorPrint.print(i + " ",ColorPrint.ANSI_PURPLE);
        }
        System.out.println();

        Arrays.stream(field.getMap()).forEach((Tile[] a) -> {
            ColorPrint.print(counter + " ",ColorPrint.ANSI_PURPLE);
            Arrays.stream(a).forEach(t -> ColorPrint.print((t.getState().equals(TileState.LIGHT_ON) ? "X" : "O") + " ", t.getState().equals(TileState.LIGHT_ON) ? ColorPrint.ANSI_YELLOW : ColorPrint.ANSI_WHITE) );
            System.out.println();
            ++counter;
        });
        counter = 0;
    }
}
