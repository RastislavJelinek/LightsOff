package lightsOff.core;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static lightsOff.core.MathUtil.inRange;
import static lightsOff.core.TileState.LIGHT_ON;

public class Field {
    private GameState state;
    private final int rowCount,columnCount;
    private int level;
    private final Tile[][] gameField;

    public Field(int rowCount, int columnCount){
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        level = 1;
        gameField = new Tile[rowCount][columnCount];
    }

    public void generate(){
        state = GameState.PLAYING;
        for(int i = 0; i < rowCount; ++i){
            for(int a = 0; a < columnCount; ++a){
                gameField[i][a] = new Tile();
            }
        }
        do {
            for (int i = 0; i < level; ++i) {
                int row = ThreadLocalRandom.current().nextInt(0, rowCount);
                int column = ThreadLocalRandom.current().nextInt(0, columnCount);
                switchTile(row, column);
            }
        }while (isSolved());
    }

    public void switchTile(int row, int column){
        if(!inRange(row, 0, rowCount))return;
        if(!inRange(column, 0, columnCount))return;
        switchTileState(row, column);

        switchTileState(row + 1, column);
        switchTileState(row - 1, column);
        switchTileState(row, column + 1);
        switchTileState(row, column - 1);
        isSolved();
    }

    private void switchTileState(int row, int column){
        if(!inRange(row, 0, rowCount))return;
        if(!inRange(column, 0, columnCount))return;
        gameField[row][column].toggleState();
    }
    
    private boolean isSolved(){
        boolean isSolved = Arrays.stream(gameField).noneMatch((Tile[] a) -> Arrays.stream(a).anyMatch(t -> t.getState().equals(LIGHT_ON)));
        if(isSolved) {
            ++level;
            state = GameState.SOLVED;
        }
        return isSolved;
    }

    public GameState getState() {
        return state;
    }

    public Tile[][] getGameField() {
        return gameField;
    }
    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getLevel() {
        return level;
    }


}
