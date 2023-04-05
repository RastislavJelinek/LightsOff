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
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game){
        return entityManager.createNamedQuery("Rating.getAverageRating",int.class)
                .setParameter("game", game).getSingleResult();
    }

    @Override
    public int getRating(String game, String player) {
        return entityManager.createNamedQuery("Rating.getRating",int.class)
                .setParameter("game", game).getSingleResult();
    }

    @Override
    public void reset(){
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
    }
}
