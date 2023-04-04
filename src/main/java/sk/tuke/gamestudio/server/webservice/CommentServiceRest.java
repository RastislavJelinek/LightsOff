package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {

    private final CommentService commentService;

    @Autowired
    public CommentServiceRest(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{game}")
    public List<Comment> getTopScores(@PathVariable String game) {
        return commentService.getComments(game);
    }

    @PostMapping
    public void addScore(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }
}
