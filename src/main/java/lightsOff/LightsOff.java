package lightsOff;

import lightsOff.consoleUi.ConsoleUi;
import lightsOff.core.Field;

public class LightsOff {
    public static void main(String[] args) {
        Field field = new Field(5, 5);
        new ConsoleUi().play(field);
    }
}
