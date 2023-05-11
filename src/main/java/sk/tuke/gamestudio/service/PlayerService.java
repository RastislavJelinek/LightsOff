package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

public interface PlayerService {
     Player getPlayer(Player player) throws PlayerException;
     void createPlayer(Player player);
}
