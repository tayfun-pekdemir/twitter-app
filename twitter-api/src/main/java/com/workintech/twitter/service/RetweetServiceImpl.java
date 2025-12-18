package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetweetServiceImpl implements RetweetService{


    private TweetRepository tweetRepository;
    private RetweetRepository retweetRepository;
    private UserService userService;

    @Autowired
    public RetweetServiceImpl(UserService userService, TweetRepository tweetRepository, RetweetRepository retweetRepository) {
        this.userService = userService;
        this.tweetRepository = tweetRepository;
        this.retweetRepository = retweetRepository;
    }

    @Override
    public Retweet retweet(Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId)
        );
        User user = userService.getCurrentUser();
        if(retweetRepository.findByUserAndTweet(user, tweet).isPresent()){
            throw new DuplicateException("You already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        tweet.incrementRetweetCount();
        tweetRepository.save(tweet);
        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet deleteRetweet(Long retweetId) {
        Retweet retweet = retweetRepository.findById(retweetId).orElseThrow(
                () -> new NotFoundException("Retweet not found by ID: " + retweetId)
        );
        User user = userService.getCurrentUser();
        if(retweet.getUser().getId().equals(user.getId())) {
            Tweet tweet = retweet.getTweet();
            tweet.decrementRetweetCount();
            tweetRepository.save(tweet);
            retweetRepository.delete(retweet);
            return retweet;
        } else {
            throw new NotAllowedException("You are not allowed to delete this retweet");
        }
    }
}
