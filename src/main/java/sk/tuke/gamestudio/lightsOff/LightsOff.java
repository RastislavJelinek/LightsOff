package sk.tuke.gamestudio.lightsOff;

import sk.tuke.gamestudio.lightsOff.consoleUi.ConsoleUi;
import sk.tuke.gamestudio.lightsOff.core.Field;

public class LightsOff {
    public static void main(String[] args) {
        Field field = new Field(5, 5);
        new ConsoleUi().play(field);
    }
}
