package sk.tuke.gamestudio.lightsOff.consoleUi;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.lightsOff.core.Field;
import sk.tuke.gamestudio.lightsOff.core.GameState;
import sk.tuke.gamestudio.lightsOff.core.Tile;
import sk.tuke.gamestudio.lightsOff.core.TileState;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    Field field;
    private int counter = 0;

    private final String game = "lightsOff";

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private  RatingService ratingService;
    @Autowired
    private CommentService commentService;


    public ConsoleUI(Field field, ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        this.field = field;
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
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

        scoreService.addScore(new Score(game,name,field.getScore(),new Date()));
        ColorPrint.println("congratulation " + name + "! your score is: " + field.getScore(),ColorPrint.ANSI_PURPLE);
        String chooser;
        do {
        System.out.print("score? / comment? / rating? / no (s/c/r/n):");
        chooser = myInput.next();
        switch (chooser) {
            case "s","score"->scoreAction(myInput);
            case "c","comment"->commentAction(myInput, name);
            case "r","rating"->ratingAction(myInput, name);
        }
        }while(chooser.equals("s") || chooser.equals("c") || chooser.equals("r"));

        ColorPrint.println("Wish you a nice day :D see you soon",ColorPrint.ANSI_YELLOW);

    }

    private void scoreAction(@NotNull Scanner myInput) {
        System.out.print("want to see TOP 10 score? / no (y/n):");
        if(myInput.next().equals("y") || myInput.next().equals("yes")){
            List<Score> lightsOff = scoreService.getTopScores(game);
            String spacer = "===============================================";
            System.out.println(spacer);
            String header = "TOP 10 scores for game: " + game;
            int spaces = (spacer.length() - header.length()) /2;
            ColorPrint.println(new String(new char[spaces]).replace('\0', ' ') + header,ColorPrint.ANSI_BLUE);
            System.out.println(spacer);
            ColorPrint.println("player | points | played on date",ColorPrint.ANSI_BLUE);
            System.out.println(spacer);
            lightsOff.forEach(t -> ColorPrint.println(t.getPlayer() +"       "+ t.getPoints() +"     " + t.getPlayedOn(), ColorPrint.ANSI_PURPLE));
            System.out.println(spacer);
        }
    }

    private void ratingAction(@NotNull Scanner myInput, String name) {
        String ratingActionChooser;
        do {
            System.out.print("want to leave rating? / see average rating? / see your previous rating? / no (l/a/p/n):");
            ratingActionChooser = myInput.next();
            switch (ratingActionChooser) {
                case "l" -> {
                    do
                        try {
                            System.out.print("give rating(1-5): ");
                            ratingService.setRating(new Rating("lightsOff", name, myInput.nextInt(), new Date()));
                            break;
                        } catch (OutOfRangeException e) {
                            System.err.println("rating not in range 1 - 5");
                        }
                    while(true);
                }
                case "a" -> ColorPrint.println("Average rating of this game: "+ ratingService.getAverageRating("lightsOff"),ColorPrint.ANSI_PURPLE);
                case "p" -> ColorPrint.println("Your previous rating: "+ ratingService.getRating("lightsOff", name),ColorPrint.ANSI_PURPLE);
            }
        }while(ratingActionChooser.equals("l") || ratingActionChooser.equals("a") || ratingActionChooser.equals("p"));
    }

    private void commentAction(@NotNull Scanner myInput, String name) {
        String commentActionChooser;
        do {
            System.out.print("want to leave comment? / see most recent comments? / no (l/s/n):");
            commentActionChooser = myInput.next();
            myInput.nextLine(); // consume leftover newline
            switch (commentActionChooser) {
                case "l" -> {
                    String comment;
                    do {
                        System.out.print("your comment(must be less then 300 characters): ");
                        comment = myInput.nextLine();
                    } while (comment.length() > 300);
                    commentService.addComment(new Comment("lightsOff", name, comment, new Date()));
                }
                case "s" -> {
                    List<Comment> coomments = commentService.getComments("lightsOff");
                    int maxPlayerLength =0;
                    int maxDateLength = 30;
                    int maxCommentLength = 0;
                    for (Comment t : coomments) {
                        maxPlayerLength = Math.max(maxPlayerLength,t.getPlayer().length());
                        maxDateLength = Math.max(maxDateLength,t.getCommentedOn().toString().length());
                        maxCommentLength = Math.max(maxCommentLength,t.getComment().length());
                    }
                    String player = getCenteredText(maxPlayerLength, "player");
                    String date = getCenteredText(maxDateLength, "played on date");
                    String comment = getCenteredText(maxCommentLength, "comment");
                    String header = "| " + player +" | "+ date +" | " + comment + " |";


                    String spacer = getRepeatCharacter(header.length(),'=');
                    System.out.println(spacer);
                    String headerText = "most recent comments for game: " + game;
                    System.out.print("|");
                    ColorPrint.print(getCenteredText(spacer.length()-2, headerText),ColorPrint.ANSI_BLUE);
                    System.out.print("|\n");
                    System.out.println(spacer);

                    System.out.print("| ");
                    ColorPrint.print(player,ColorPrint.ANSI_BLUE);
                    System.out.print(" | ");
                    ColorPrint.print(date,ColorPrint.ANSI_BLUE);
                    System.out.print(" | ");
                    ColorPrint.print(comment,ColorPrint.ANSI_BLUE);
                    System.out.print(" |\n");
                    System.out.println(spacer);

                    coomments.forEach(t -> {
                        System.out.print("| ");
                        ColorPrint.print(t.getPlayer()+ getRepeatCharacter(player.length() - t.getPlayer().length(), ' '), ColorPrint.ANSI_PURPLE);
                        System.out.print(" | ");
                        ColorPrint.print(t.getCommentedOn()+ getRepeatCharacter(date.length() - t.getCommentedOn().toString().length(), ' '), ColorPrint.ANSI_PURPLE);
                        System.out.print(" | ");
                        ColorPrint.print(t.getComment()+ getRepeatCharacter(comment.length() - t.getComment().length(), ' '), ColorPrint.ANSI_PURPLE);
                        System.out.print(" |\n");
                    });

                    System.out.println(spacer);
                }
            }
        }while(commentActionChooser.equals("l") || commentActionChooser.equals("s"));
    }

    @NotNull
    private String getRepeatCharacter(int maxPlayerLength, char character) {
        if(maxPlayerLength<0)return "";
        return new String(new char[maxPlayerLength]).replace('\0', character);
    }

    @NotNull
    private String getCenteredText(int length, String message) {
        return getRepeatCharacter((length - message.length())/2, ' ') + message + getRepeatCharacter((length - message.length())/2, ' ');
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
