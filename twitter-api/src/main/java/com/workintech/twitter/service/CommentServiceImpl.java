package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
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
        User user = userRepository.findById(userId).orElseThrow(/*Exception*/);
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(/*Exception*/);
        comment.setUser(user);
        comment.setTweet(tweet);
        return commentRepository.save(comment) ;
    }

    @Override
    public Comment updateComment(Long commentId,Long userId, Comment comment) {
        Comment existingComment = commentRepository.findById(commentId).orElseThrow(/*Exception*/);
        if(existingComment.getUser().getId().equals(userId)){
            existingComment.setContent(comment.getContent());
            return commentRepository.save(existingComment);
        } else {
            throw new RuntimeException("You are not allowed to update this comment");
        }
    }

    @Override
    public Comment deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(/*Exception*/);
        if(comment.getUser().getId().equals(userId) || comment.getTweet().getUser().getId().equals(userId)) {
            commentRepository.delete(comment);
            return comment;
        } else {
            throw new RuntimeException("You are not allowed to delete this comment");
        }
    }
}
