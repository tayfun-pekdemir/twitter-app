package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{

    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository,UserRepository userRepository){
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Tweet createTweet(Long userId, Tweet tweet) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found by ID: " + userId)
        );
        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findTweetsByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    @Override
    public Tweet findById(Long tweetId) {
        return tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId)
        );
    }

    @Override
    public Tweet updateTweet(Long tweetId, Long userId, Tweet tweet) {
        Tweet existingTweet = findById(tweetId);
        if (existingTweet.getUser().getId().equals(userId)) {
            existingTweet.setContent(tweet.getContent());
            return tweetRepository.save(existingTweet);
        } else {
            throw new NotAllowedException("You are not allowed to update this tweet");
        }
    }

    @Override
    public Tweet deleteTweet(Long tweetId, Long userId) {
        Tweet tweet = findById(tweetId);
        if(tweet.getUser().getId().equals(userId)){
            tweetRepository.delete(tweet);
            return tweet;
        } else {
            throw new NotAllowedException("You are not allowed to delete this tweet");
        }

    }
}
