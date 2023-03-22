package sk.tuke.gamestudio.entity;


import java.util.Date;

public class Comment extends Entity {
    private String comment;
    private Date commentedOn;

    public Comment(String game, String player, String comment, Date playedOn) {
        super(game,player);
        this.comment = comment;
        this.commentedOn = playedOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + getGame() + '\'' +
                ", player='" + getPlayer() + '\'' +
                ", comment=" + getComment() +
                ", playedOn=" + getCommentedOn() +
                '}';
    }
}
