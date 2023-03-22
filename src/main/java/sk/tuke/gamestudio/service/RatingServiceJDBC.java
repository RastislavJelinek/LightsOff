package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

public class RatingServiceJDBC implements RatingService{

    public static final String URL = "jdbc:postgresql://localhost/lightsOff";
    public static final String USER = "postgres";
    public static final String PASSWORD = "32923292";
    public static final String SELECT = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    public static final String AVERAGE = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String DELETE = "TRUNCATE rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?) ON CONFLICT (game, player) DO UPDATE SET rating = EXCLUDED.rating,ratedOn = EXCLUDED.ratedOn";


    @Override
    public void setRating(Rating rating){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem inserting or changing score", e);
        }
    }

    @Override
    public int getAverageRating(String game){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(AVERAGE)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new RatingException("No ratings found for game " + game);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting average of game: " + game, e);
        }
    }

    @Override
    public int getRating(String game, String player){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new RatingException("Rating not found for player " + player + " in game " + game);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting score of player " + player, e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting ratings", e);
        }
    }
}
