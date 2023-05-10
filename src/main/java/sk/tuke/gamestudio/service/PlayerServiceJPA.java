package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class PlayerServiceJPA implements PlayerService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Player> getPlayer(Player player) {
        return Optional.ofNullable(entityManager.createNamedQuery("Player.getPassword", Player.class)
                .setParameter("username", player.getUsername())
                .getSingleResult());
    }

    @Override
    public void createPlayer(Player player) {
        entityManager.persist(player);
    }


}