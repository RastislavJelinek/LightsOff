package sk.tuke.gamestudio.server.controller;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.lightsOff.core.Field;
import sk.tuke.gamestudio.service.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LightsOffController {

    private Field field;
    private final PasswordGenerator passwordGenerator = new PasswordGenerator();
    private final ScoreService scoreService;
    private final RatingService ratingService;
    private final CommentService commentService;


    private final PlayerService playerService;
    private Player player;

    public LightsOffController(PlayerService playerService, RatingService ratingService, CommentService commentService, ScoreService scoreService){
        player = new Player();
        newGame();
        this.playerService = playerService;
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }



    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/game")
    public String hello() {
        return "game";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/credits")
    public String credits() {
        return "credits";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/gameSettings")
    public String gameSettings() {
        return "gameSettings";
    }

    @GetMapping("/showScoreBoard")
    public String showScoreBoard() {
        return "scoreBoard";
    }

    @GetMapping("/showComments")
    public String showComments() {
        return "comments";
    }


    @PostMapping("/switchLight")
    public String switchLight(@RequestBody Map<String, Integer> requestBody) {
        Integer row = requestBody.get("row");
        Integer column = requestBody.get("column");
        if (row != null && column != null) {
            field.switchTile(row, column);
        }
        return "game";
    }

    @PostMapping("/nextLevel")
    public String nextLevel() {
        field.nextLevel();
        field.generate();
        return "game";
    }
    @PostMapping("/newGame")
    public String newGame() {
        field = new Field(9, 9);
        field.nextLevel();
        field.generate();
        return "game";
    }

    @GetMapping("/field")
    @ResponseBody
    public Field field() {
        return field;
    }

    @GetMapping("/scoreboard")
    @ResponseBody
    public List<Score> scoreboard() {
        return scoreService.getTopScores("lightsOff");
    }

    @GetMapping("/comments")
    @ResponseBody
    public  List<Comment> comments() {
        return commentService.getComments("lightsOff");
    }

    @GetMapping("/rating")
    @ResponseBody
    public int rating() {
        if(player.getUsername() == null)return -1;
        return ratingService.getRating("lightsOff",player.getUsername());
    }




    @PostMapping("/loginpass")
    @ResponseBody
    public boolean loginPass(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        if (username == null && password == null) {
            return false;
        }

        Player k = new Player();
        k.setUsername(username);
        Optional<Player> password1 = playerService.getPlayer(k);
        if(password1.isEmpty()){
            return false;
        }
        String hashedPassword = passwordGenerator.generateHashedPassword(password,password1.get().getSalt());
        player = k;
        return Objects.equals(hashedPassword, password1.get().getUserpassword());
    }




    @PostMapping("/registeruser")
    @ResponseBody
    public boolean registerUser(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        if (username == null && password == null) {
            return false;
        }

        byte[] salt = passwordGenerator.generateSalt();
        String hashedPassword = passwordGenerator.generateHashedPassword(password, salt);

        Player player = new Player();
        player.setUsername(username);
        player.setUserpassword(hashedPassword);
        player.setSalt(salt);
        playerService.createPlayer(player);

        return true;
    }


}


