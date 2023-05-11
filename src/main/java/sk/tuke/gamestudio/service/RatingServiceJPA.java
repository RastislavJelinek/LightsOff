package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) {
        Rating rate = entityManager.createNamedQuery("Rating.getRating", Rating.class)
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .getSingleResult();
        if(rate != null){
            rating.setIdent(rate.getIdent());
        }
        entityManager.merge(rating);
    }

    @Override
    public int getAverageRating(String game){
        try {
            Double avgRating = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                    .setParameter("game", game)
                    .getSingleResult();
            return avgRating.intValue();
        } catch (RatingException e) {
            throw new RatingException("problém získať priemerné skóre");
        }
    }

    @Override
    public int getRating(String game, String player) {
        try {
            Rating rating = entityManager.createNamedQuery("Rating.getRating", Rating.class)
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();
            return rating.getRating();
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @Override
    public void reset(){
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
    }
}
