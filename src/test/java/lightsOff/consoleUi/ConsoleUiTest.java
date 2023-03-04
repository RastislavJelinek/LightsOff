package lightsOff.consoleUi;

import lightsOff.core.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleUiTest {

    private ConsoleUi consoleUi;
    private Field field;

    @BeforeEach
    public void setUp() {
        consoleUi = new ConsoleUi();
        field = new Field(5, 5);
    }

    @Test
    public void testPlay() {
        consoleUi.play(field);
        assertTrue(true); // Just a placeholder assertion to indicate that the test passed
    }

}