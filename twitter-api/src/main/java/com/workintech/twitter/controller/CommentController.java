package com.workintech.twitter.controller;

import com.workintech.twitter.dto.CommentRequest;
import com.workintech.twitter.dto.CommentResponse;
import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.service.CommentService;
import jakarta.validation.Valid;
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
    public CommentResponse createComment(@Valid @RequestBody CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());

        Comment savedComment = commentService.createComment(request.getTweetId(), comment);

        return new CommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getId(),
                savedComment.getTweet().getId()
        );
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());

        Comment updatedComment = commentService.updateComment(commentId, comment);

        return new CommentResponse(
                updatedComment.getId(),
                updatedComment.getContent(),
                updatedComment.getUser().getId(),
                updatedComment.getTweet().getId()
        );
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse deleteComment(@PathVariable Long commentId) {
        Comment deletedComment = commentService.deleteComment(commentId);

        return new CommentResponse(
                deletedComment.getId(),
                deletedComment.getContent(),
                deletedComment.getUser().getId(),
                deletedComment.getTweet().getId()
        );
    }
}
