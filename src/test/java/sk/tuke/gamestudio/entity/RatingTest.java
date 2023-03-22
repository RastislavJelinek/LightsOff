package sk.tuke.gamestudio.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    private static Rating rating;

    @BeforeAll
    public static void setUp() throws Exception {
        Date date = new Date();
        rating = new Rating("game", "player", 4, date);
    }

    @Test
    public void testConstructor() {
        assertNotNull(rating);
    }

    @Test
    public void testGetRating() throws OutOfRangeException {
        Rating rating = new Rating("game", "player", 3, new Date());
        assertEquals(3, rating.getRating());
    }

    @Test
    public void testSetRating() {
        rating.setRating(5);
        assertEquals(5, rating.getRating());
    }

    @Test
    public void testGetRatedOn() {
        Date expected = rating.getRatedOn();
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String expectedString = format.format(expected);
        String actualString = format.format(rating.getRatedOn());
        assertEquals(expectedString, actualString);
    }


    @Test
    public void testSetRatedOn() {
        Date date = new Date();
        rating.setRatedOn(date);
        assertEquals(date, rating.getRatedOn());
    }

    @Test
    public void testToString() {
        String game = rating.getGame();
        String player = rating.getPlayer();
        int points = rating.getRating();
        Date playedOn = rating.getRatedOn();
        String expected = String.format("Score{game='%s', player='%s', points=%d, playedOn=%s}", game, player, points, playedOn);
        assertEquals(expected, rating.toString());
    }

    @Test
    public void testConstructorWithInvalidRating() {
        Date date = new Date();
        OutOfRangeException exception = assertThrows(OutOfRangeException.class,
                () -> new Rating("game", "player", 6, date));
        assertEquals("rating is out of range", exception.getMessage());
    }
}
