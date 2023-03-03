package lightsOff.consoleUi;

import lightsOff.core.Field;
import lightsOff.core.GameState;
import lightsOff.core.Tile;

import java.util.Arrays;
import java.util.Scanner;

import static lightsOff.core.TileState.LIGHT_ON;

public class ConsoleUi {

    Field field;
    private int counter = 0;


    public void play(Field field) {
        this.field = field;
        Scanner myInput = new Scanner( System.in );
        do {
            field.generate();
            do {
                show();
                handleInput();
            } while (field.getState() == GameState.PLAYING);

            show();
            System.out.println("Solved!");
            System.out.print("want to play next level?(y/n):");
        }while(!myInput.next().equals("n"));
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
        Tile[][] gameField = field.getGameField();
        System.out.println("level: " + field.getLevel());
        System.out.print("  ");
        for(int i = 0; i < field.getColumnCount(); ++i){
            System.out.print(i + " ");
        }
        System.out.println();

        Arrays.stream(gameField).forEach((Tile[] a) -> {
            System.out.print(counter + " ");
            Arrays.stream(a).forEach(t -> System.out.print((t.getState().equals(LIGHT_ON) ? "X" : "O") + " ") );
            System.out.println();
            ++counter;
        });
        counter = 0;
    }
}
