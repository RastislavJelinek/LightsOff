package sk.tuke.gamestudio.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery( name = "Player.getPassword",
        query = "SELECT p FROM Player p WHERE p.username = :username")
public class Player {

    @Id
    @GeneratedValue
    private int ident;
    private String username;
    private String userpassword;
    private byte[] salt;


    public Player(String username, String userpassword, byte[] salt) {
        this.username = username;
        this.userpassword = userpassword;
        this.salt = salt;
    }
    public Player() {
    }

    public String getUsername() {
        return username;
    }
    public String getUserpassword() {
        return userpassword;
    }


    public void setUserpassword(String password) {
        this.userpassword = password;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + getUsername() + '\'' +
                ", password='" + getUserpassword() +
                '}';
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
