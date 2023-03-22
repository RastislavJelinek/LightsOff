package sk.tuke.gamestudio.entity;

public abstract class Entity {

    private String game;

    private String player;

    public Entity(String game, String player) {
        this.game = game;
        this.player = player;
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
}
