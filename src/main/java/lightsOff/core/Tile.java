package lightsOff.core;

import static lightsOff.core.TileState.LIGHT_OFF;
import static lightsOff.core.TileState.LIGHT_ON;

public class Tile {
    private TileState state;

    public Tile(){
        this.state = LIGHT_OFF;
    }


    public TileState getState() {
        return state;
    }

    protected void toggleState() {
        this.state = (getState().equals(LIGHT_ON)) ? LIGHT_OFF : LIGHT_ON;
    }
}
