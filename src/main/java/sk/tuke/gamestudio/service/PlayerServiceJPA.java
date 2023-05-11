package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class PlayerServiceJPA implements PlayerService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player getPlayer(Player player) {
        try {
            return entityManager.createNamedQuery("Player.getPassword", Player.class)
                    .setParameter("username", player.getUsername())
                    .getSingleResult();
        }catch (PlayerException e){
            throw new PlayerException("hráč sa nenašiel");
        }


    }

    @Override
    public void createPlayer(Player player) {
        entityManager.persist(player);
    }


}