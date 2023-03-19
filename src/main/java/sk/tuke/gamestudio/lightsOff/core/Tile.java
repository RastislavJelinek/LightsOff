package sk.tuke.gamestudio.lightsOff.core;

public class Tile {
    private TileState state;

    public Tile(){
        this.state = TileState.LIGHT_OFF;
    }


    public TileState getState() {
        return state;
    }

    public void toggleState() {
        this.state = (getState().equals(TileState.LIGHT_ON)) ? TileState.LIGHT_OFF : TileState.LIGHT_ON;
    }
}
