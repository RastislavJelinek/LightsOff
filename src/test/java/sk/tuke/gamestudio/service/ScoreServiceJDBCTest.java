package sk.tuke.gamestudio.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;
import java.util.List;

class ScoreServiceJDBCTest {

    private static ScoreServiceJDBC scoreService;

    @BeforeAll
    static void setUp() {
        scoreService = new ScoreServiceJDBC();
    }

    @BeforeEach
    void init() {
        scoreService.reset();
    }

    @Test
    void testAddScore() {
        Score score = new Score("game", "player", 100, new Date());
        scoreService.addScore(score);
        List<Score> scores = scoreService.getTopScores("game");
        Assertions.assertEquals(1, scores.size());
        Score retrievedScore = scores.get(0);
        Assertions.assertEquals(score.getGame(), retrievedScore.getGame());
        Assertions.assertEquals(score.getPlayer(), retrievedScore.getPlayer());
        Assertions.assertEquals(score.getPoints(), retrievedScore.getPoints());
        Assertions.assertEquals(score.getPlayedOn(), retrievedScore.getPlayedOn());
    }

    @Test
    void testGetTopScores() {
        Score score1 = new Score("game", "player1", 100, new Date());
        Score score2 = new Score("game", "player2", 200, new Date());
        Score score3 = new Score("game", "player3", 300, new Date());
        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);
        List<Score> scores = scoreService.getTopScores("game");
        Assertions.assertEquals(3, scores.size());
        Assertions.assertEquals(score3.getPlayedOn(), scores.get(0).getPlayedOn());
        Assertions.assertEquals(score2.getPlayedOn(), scores.get(1).getPlayedOn());
        Assertions.assertEquals(score1.getPlayedOn(), scores.get(2).getPlayedOn());
    }

    @Test
    void testReset() {
        Score score = new Score("game", "player", 100, new Date());
        scoreService.addScore(score);
        scoreService.reset();
        List<Score> scores = scoreService.getTopScores("game");
        Assertions.assertTrue(scores.isEmpty());
    }
}

