package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreExceptionTest {

    @Test
    public void testScoreExceptionWithMessage() {
        ScoreException exception = assertThrows(ScoreException.class, () -> {
            throw new ScoreException("Invalid score");
        });

        assertEquals("Invalid score", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testScoreExceptionWithMessageAndCause() {
        Exception cause = new Exception("Some cause");
        ScoreException exception = assertThrows(ScoreException.class, () -> {
            throw new ScoreException("Invalid score", cause);
        });

        assertEquals("Invalid score", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}