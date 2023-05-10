package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
@EntityScan("sk.tuke.gamestudio.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }
    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
    @Bean
    public CommentService commentService() {return new CommentServiceJPA();}
    @Bean
    public PlayerServiceJPA playerService() {return new PlayerServiceJPA();}

}
