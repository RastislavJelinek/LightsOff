package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Objects;

public class RatingServiceRestClient implements RatingService{

    private final String url = "http://localhost:8080/api/rating";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setRating(Rating rating){
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        return Objects.requireNonNull(restTemplate.getForObject(url + "/" + game, int.class));
    }

    @Override
    public int getRating(String game, String player){
        return Objects.requireNonNull(restTemplate.getForObject(url +"/" + game +"/"+ player, int.class));
    }

    @Override
    public void reset(){
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
