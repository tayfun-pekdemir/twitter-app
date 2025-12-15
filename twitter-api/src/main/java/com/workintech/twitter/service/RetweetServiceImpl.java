package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.RetweetRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetweetServiceImpl implements RetweetService{

    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private RetweetRepository retweetRepository;
    @Autowired
    public RetweetServiceImpl(UserRepository userRepository, TweetRepository tweetRepository, RetweetRepository retweetRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.retweetRepository = retweetRepository;
    }

    @Override
    public Retweet retweet(Long userId, Long tweetId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId)
        );
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found by ID: " + userId)
        );

        if(retweetRepository.findByUserAndTweet(user, tweet).isPresent()){
            throw new DuplicateException("You already retweeted this tweet");
        }

        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet deleteRetweet(Long retweetId,Long userId) {
        Retweet retweet = retweetRepository.findById(retweetId).orElseThrow(
                () -> new NotFoundException("Retweet not found by ID: " + retweetId)
        );
        if(retweet.getUser().getId().equals(userId)) {
            retweetRepository.delete(retweet);
            return retweet;
        } else {
            throw new NotAllowedException("You are not allowed to delete this retweet");
        }
    }
}
