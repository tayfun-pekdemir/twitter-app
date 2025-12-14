package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;

import java.util.List;

public interface TweetService {

    Tweet createTweet(Long userId,Tweet tweet);
    List<Tweet> findTweetsByUserId(Long userId);
    Tweet findById(Long tweetId);
    Tweet updateTweet(Long tweetId,Long userId,Tweet tweet);
    Tweet deleteTweet(Long tweetId,Long userId);


}
