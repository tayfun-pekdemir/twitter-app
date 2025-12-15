package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.CommentRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(UserRepository userRepository, TweetRepository tweetRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Long userId,Long tweetId,Comment comment) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found by ID: " + userId));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId));
        comment.setUser(user);
        comment.setTweet(tweet);
        return commentRepository.save(comment) ;
    }

    @Override
    public Comment updateComment(Long commentId,Long userId, Comment comment) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found by ID: " + commentId)
        );
        if(existingComment.getUser().getId().equals(userId)){
            existingComment.setContent(comment.getContent());
            return commentRepository.save(existingComment);
        } else {
            throw new NotAllowedException("You are not allowed to update this comment");
        }
    }

    @Override
    public Comment deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found by ID: " + commentId)
        );
        if(comment.getUser().getId().equals(userId) || comment.getTweet().getUser().getId().equals(userId)) {
            commentRepository.delete(comment);
            return comment;
        } else {
            throw new NotAllowedException("You are not allowed to delete this comment") ;
        }
    }
}
