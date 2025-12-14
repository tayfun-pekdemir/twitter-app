package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestParam Long userId,@RequestParam Long tweetId,@RequestBody Comment comment) {
        return commentService.createComment(userId,tweetId,comment);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateComment(@PathVariable Long commentId,@RequestParam Long userId,@RequestBody Comment comment){
        return commentService.updateComment(commentId,userId,comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public  Comment deleteComment(@PathVariable Long commentId,@RequestParam Long userId){
        return commentService.deleteComment(commentId,userId);
    }
}
