package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceJDBCTest {
    private static final String URL = "jdbc:postgresql://localhost/lightsOff";
    private static final String USER = "postgres";
    private static final String PASSWORD = "32923292";

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceJDBC();
        commentService.reset();
    }

    @Test
    @DisplayName("Test addComment() method")
    void testAddComment() {
        Comment comment = new Comment("lightsOff", "player1", "test comment", new Date());
        commentService.addComment(comment);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM comment WHERE game = ?")
        ) {
            statement.setString(1, comment.getGame());
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(1, rs.getInt(1));
            }
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test getComments() method")
    void testGetComments() {
        Comment comment1 = new Comment("lightsOff", "player1", "test comment 1", new Date());
        Comment comment2 = new Comment("lightsOff", "player2", "test comment 2", new Date());
        Comment comment3 = new Comment("lightsOff", "player3", "test comment 3", new Date());
        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);

        List<Comment> comments = commentService.getComments("lightsOff");
        assertEquals(3, comments.size());
        assertEquals(comment3.getCommentedOn(), comments.get(0).getCommentedOn());
        assertEquals(comment2.getCommentedOn(), comments.get(1).getCommentedOn());
        assertEquals(comment1.getCommentedOn(), comments.get(2).getCommentedOn());
    }

    @Test
    @DisplayName("Test reset() method")
    void testReset() {
        Comment comment = new Comment("lightsOff", "player1", "test comment", new Date());
        commentService.addComment(comment);
        commentService.reset();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM comment")
        ) {
            try (ResultSet rs = statement.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(0, rs.getInt(1));
            }
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
}
