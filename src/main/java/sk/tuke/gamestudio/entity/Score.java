package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Score extends Entity {

    private int points;

    private Date playedOn;

    public Score(String game, String player, int points, Date playedOn) {
        super(game,player);
        this.points = points;
        this.playedOn = playedOn;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + getGame() + '\'' +
                ", player='" + getPlayer() + '\'' +
                ", points=" + getPoints() +
                ", playedOn=" + getPlayedOn() +
                '}';
    }

}
