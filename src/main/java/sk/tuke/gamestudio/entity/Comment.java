package sk.tuke.gamestudio.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;
@Entity
@NamedQuery( name = "Comment.getRecentComments",
        query = "SELECT c FROM Comment  c WHERE c.game =: game ORDER BY c.commentedOn DESC")
@NamedQuery( name = "Comment.resetComment",
        query = "DELETE FROM Comment")
public class Comment  implements Serializable {

    @Id
    @GeneratedValue
    private int ident;
    private String comment;
    private Date commentedOn;
    private String game;

    private String player;



    public Comment(String game, String player, String comment, Date playedOn) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.commentedOn = playedOn;
    }

    public Comment() {

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
