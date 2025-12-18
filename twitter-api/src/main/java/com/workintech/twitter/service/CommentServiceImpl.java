package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private UserService userService;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(UserService userService, TweetRepository tweetRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Long tweetId,Comment comment) {
        User user = userService.getCurrentUser();
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId));
        tweet.incrementCommentCount();
        tweetRepository.save(tweet);
        comment.setUser(user);
        comment.setTweet(tweet);
        return commentRepository.save(comment) ;
    }

    @Override
    public Comment updateComment(Long commentId, Comment comment) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found by ID: " + commentId)
        );
        User user = userService.getCurrentUser();
        if(existingComment.getUser().getId().equals(user.getId())){
            existingComment.setContent(comment.getContent());
            return commentRepository.save(existingComment);
        } else {
            throw new NotAllowedException("You are not allowed to update this comment");
        }
    }

    @Override
    public Comment deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found by ID: " + commentId)
        );
        User user = userService.getCurrentUser();
        if(comment.getUser().getId().equals(user.getId()) || comment.getTweet().getUser().getId().equals(user.getId())) {
            Tweet tweet = comment.getTweet();
            tweet.decrementCommentCount();
            tweetRepository.save(tweet);
            commentRepository.delete(comment);
            return comment;
        } else {
            throw new NotAllowedException("You are not allowed to delete this comment") ;
        }
    }
}
