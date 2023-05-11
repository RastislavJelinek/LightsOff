package sk.tuke.gamestudio.lightsOff.core;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static sk.tuke.gamestudio.lightsOff.math.MathUtil.inRange;

public class Field {
    private GameState state;
    private int rowCount,columnCount;
    private int level;
    private int moveCounter;
    private Tile[][] map;

    public void setState(GameState state) {
        this.state = state;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    public Field(){}

    public Field(int rowCount, int columnCount){
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        level = 0;
        moveCounter = 0;
        map = new Tile[rowCount][columnCount];
    }

    public void generate(){
        int k = moveCounter;
        state = GameState.PLAYING;
        for(int i = 0; i < rowCount; ++i)
            for (int a = 0; a < columnCount; ++a) map[i][a] = new Tile();

        do for (int i = 0; i < level; ++i) {
            int row = ThreadLocalRandom.current().nextInt(0, rowCount);
            int column = ThreadLocalRandom.current().nextInt(0, columnCount);
            switchTile(row, column);
        } while (isSolved());
        moveCounter = k;
    }

    public void switchTile(int row, int column){
        if(!inRange(row, 0, rowCount - 1))return;
        if(!inRange(column, 0, columnCount - 1))return;
        ++moveCounter;
        switchTileState(row, column);

        switchTileState(row + 1, column);
        switchTileState(row - 1, column);
        switchTileState(row, column + 1);
        switchTileState(row, column - 1);
        isSolved();
    }

    private void switchTileState(int row, int column){
        if(!inRange(row, 0, rowCount - 1))return;
        if(!inRange(column, 0, columnCount - 1))return;
        map[row][column].toggleState();
    }
    
    public boolean isSolved(){
        boolean isSolved = Arrays.stream(map).noneMatch((Tile[] a) -> Arrays.stream(a).anyMatch(t -> t.getState().equals(TileState.LIGHT_ON)));
        if(isSolved) {
            state = GameState.SOLVED;
        }
        return isSolved;
    }

    public int getScore(){
        int levelSum = 0;
        for(int i = 0; i <= level; ++i){
            levelSum = levelSum + i;
        }

        return levelSum - moveCounter;
    }
    public void nextLevel(){
        ++level;
    }

    public GameState getState() {
        return state;
    }

    public Tile[][] getMap() {
        return map;
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
