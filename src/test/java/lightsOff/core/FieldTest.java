package lightsOff.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static lightsOff.core.GameState.PLAYING;
import static lightsOff.core.GameState.SOLVED;
import static lightsOff.math.MathUtil.inRange;
import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private final Field field = new Field(5, 5);
    private final Tile[][] clone = new Tile[5][5];
    private Tile[][] map = new Tile[5][5];
    @BeforeEach
    public void test_setUp(){
        field.generate();
        map = field.getMap();
        Tile[][] gameField = field.getMap();
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                clone[i][a] = new Tile();
                if(gameField[i][a].getState() == TileState.LIGHT_ON)
                    clone[i][a].toggleState();
            }
        }


    }


    @Test
    public void test_mapGeneration(){
        boolean isOn = false;
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                if(map[i][a].getState().equals(TileState.LIGHT_ON)){
                    isOn = true;
                    break;
                }

            }
        }
        assertTrue(isOn);
    }

    @Test
    public void test_switchingLightOutOfRangeShouldDoNothing(){
        field.switchTile(-1,-1);
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                assertEquals(map[i][a].getState(), clone[i][a].getState());
            }
        }
    }

    @Test
    public void test_switchingLightShouldSwitchTileState(){
        int switchedRow = 3;
        int switchedColumn = 2;
        field.switchTile(switchedRow,switchedColumn);

        for(int row = 0; row < field.getRowCount(); ++row){
            for(int column = 0; column < field.getColumnCount(); ++column){
                if(row == switchedRow && inRange(column,switchedColumn -1,switchedColumn + 1)){
                    assertNotEquals(map[row][column].getState(), clone[row][column].getState());
                    break;
                }
                if(column == switchedColumn && inRange(row,switchedRow -1,switchedRow + 1)){
                    assertNotEquals(map[row][column].getState(), clone[row][column].getState());
                    break;
                }
                assertEquals(map[row][column].getState(), clone[row][column].getState());
            }
        }
    }

    @Test
    public void test_levelOnStartUpShouldBeOne(){
        int level = field.getLevel();
        assertEquals(1,level);
    }

    @Test
    public void test_stateOnStartupShouldBePlaying(){
        assertEquals(PLAYING,field.getState());
    }

    @Test
    public void test_mapIsSolvedWhenAllLightsAreOff(){
        Tile[][] gameField = field.getMap();
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                if(gameField[i][a].getState() == TileState.LIGHT_ON)
                    gameField[i][a].toggleState();
            }
        }
        assertTrue(field.isSolved());
    }

    @Test
    public void test_stateShouldBeSolvedWhenSolved(){
        Tile[][] gameField = field.getMap();
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                if(gameField[i][a].getState() == TileState.LIGHT_ON)
                    gameField[i][a].toggleState();
            }
        }
        field.isSolved();
        assertEquals(SOLVED,field.getState());
    }

    @Test
    public void test_levelShouldIncreaseWhenLevelFinished(){
        Tile[][] gameField = field.getMap();
        for(int i = 0; i < field.getRowCount(); ++i){
            for(int a = 0; a < field.getColumnCount(); ++a){
                if(gameField[i][a].getState() == TileState.LIGHT_ON)
                    gameField[i][a].toggleState();
            }
        }
        field.isSolved();
        assertEquals(2,field.getLevel());
    }

    @Test
    public void test_mapIsNotSolvedWhenAllNotLightsAreOff(){
        assertFalse(field.isSolved());
    }



}