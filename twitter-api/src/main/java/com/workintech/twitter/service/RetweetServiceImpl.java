package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
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
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(/*Exception*/);
        User user = userRepository.findById(userId).orElseThrow(/*Exception*/);
        Retweet retweet = new Retweet();
        retweet.setUser(user);
        retweet.setTweet(tweet);
        return retweetRepository.save(retweet);
    }

    @Override
    public Retweet deleteRetweet(Long retweetId,Long userId) {
        Retweet retweet = retweetRepository.findById(retweetId).orElseThrow(/*Exception*/);
        if(retweet.getUser().getId().equals(userId)) {
            retweetRepository.delete(retweet);
            return retweet;
        } else {
            throw new RuntimeException("You are not allowed to delete this retweet");
        }
    }
}
