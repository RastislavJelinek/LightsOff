package sk.tuke.gamestudio.entity;


import sk.tuke.gamestudio.lightsOff.math.MathUtil;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery( name = "Rating.getRating",
        query = "SELECT r FROM Rating r WHERE r.game =: game AND r.player =: player")
@NamedQuery( name = "Rating.getAverageRating",
        query = "SELECT AVG(r) FROM Rating r WHERE r.game =: game ")
@NamedQuery( name = "Rating.resetRating",
        query = "DELETE FROM Score")
public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int ident;
    private int rating;

    private Date ratedOn;

    private String game;

    private String player;

    public Rating(String game, String player, int rating, Date ratedOn) throws OutOfRangeException {
        this.game = game;
        this.player = player;
        boolean b = MathUtil.inRange(rating, 1, 5);
        if(!b)throw new OutOfRangeException("rating is out of range");
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public Rating() {
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + getGame() + '\'' +
                ", player='" + getPlayer() + '\'' +
                ", points=" + getRating() +
                ", playedOn=" + getRatedOn() +
                '}';
    }
}
