package sk.tuke.gamestudio.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

public class RatingServiceJDBCTest {

    private static final String URL = "jdbc:postgresql://localhost/lightsOff";
    private static final String USER = "postgres";
    private static final String PASSWORD = "32923292";

    private RatingServiceJDBC ratingService;

    @BeforeEach
    public void setUp() {
        ratingService = new RatingServiceJDBC();
        ratingService.reset();
    }

    @Test
    @DisplayName("Test setRating() method")
    public void testSetRating() throws RatingException, OutOfRangeException {
            Rating rating = new Rating("lightsOff", "player1", 4, new java.util.Date());
            ratingService.setRating(rating);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT rating FROM rating WHERE game = 'lightsOff' AND player = 'player1'")
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(4, rs.getInt(1));
                assertFalse(rs.next());
            }

        } catch (SQLException e) {
            System.err.println("something went wonk");
        }
    }

    @Test
    @DisplayName("Test getAverageRating() method")
    public void testGetAverageRating() throws RatingException, OutOfRangeException {
            Rating rating1 = new Rating("lightsOff", "player1", 3, new java.util.Date());
            Rating rating2 = new Rating("lightsOff", "player2", 5, new java.util.Date());
            ratingService.setRating(rating1);
            ratingService.setRating(rating2);

            int averageRating = ratingService.getAverageRating("lightsOff");
            assertEquals(4, averageRating);

    }

    @Test
    @DisplayName("Test getRating() method")
    public void testGetRating() throws RatingException, OutOfRangeException {
            Rating rating = new Rating("lightsOff", "player1", 4, new java.util.Date());
            ratingService.setRating(rating);

            int playerRating = ratingService.getRating("lightsOff", "player1");
            assertEquals(4, playerRating);

    }

    @Test
    @DisplayName("Test reset() method")
    public void testReset() throws RatingException, OutOfRangeException {
        Rating rating = new Rating("lightsOff", "player1", 4, new java.util.Date());
        ratingService.setRating(rating);

        ratingService.reset();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM rating")
        ){
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(0, rs.getInt(1));
                assertFalse(rs.next());
            }

        } catch (SQLException e) {
            System.err.println("something went wonk");
        }

    }

}
