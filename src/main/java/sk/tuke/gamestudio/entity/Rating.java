package sk.tuke.gamestudio.entity;


import sk.tuke.gamestudio.lightsOff.math.MathUtil;
import sk.tuke.gamestudio.lightsOff.math.OutOfRangeException;

import java.util.Date;

public class Rating extends Entity {

    private int rating;

    private Date ratedOn;

    public Rating(String game, String player, int rating, Date ratedOn) throws OutOfRangeException {
        super(game,player);
        boolean b = MathUtil.inRange(rating, 1, 5);
        if(!b)throw new OutOfRangeException("rating is out of range");
        this.rating = rating;
        this.ratedOn = ratedOn;
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
