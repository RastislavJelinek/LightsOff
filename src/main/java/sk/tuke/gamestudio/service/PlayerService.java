package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import java.util.Optional;

public interface PlayerService {
     Optional<Player> getPlayer(Player player);
     void createPlayer(Player player);
}
