package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{

    private TweetRepository tweetRepository;
    private UserService userService;
    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    @Override
    public Tweet createTweet(Tweet tweet) {

        User user = userService.getCurrentUser();
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
    public Tweet updateTweet(Long tweetId, Tweet tweet) {
        Tweet existingTweet = findById(tweetId);
        User user = userService.getCurrentUser();
        if (existingTweet.getUser().getId().equals(user.getId())) {
            existingTweet.setContent(tweet.getContent());
            return tweetRepository.save(existingTweet);
        } else {
            throw new NotAllowedException("You are not allowed to update this tweet");
        }
    }

    @Override
    public Tweet deleteTweet(Long tweetId) {
        Tweet tweet = findById(tweetId);
        User user = userService.getCurrentUser();
        if(tweet.getUser().getId().equals(user.getId())){
            tweetRepository.delete(tweet);
            return tweet;
        } else {
            throw new NotAllowedException("You are not allowed to delete this tweet");
        }

    }
}
