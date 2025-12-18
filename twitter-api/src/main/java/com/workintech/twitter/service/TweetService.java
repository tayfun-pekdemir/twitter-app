package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;

import java.util.List;

public interface TweetService {

    Tweet createTweet(Tweet tweet);

    List<Tweet> findTweetsByUserId(Long userId);

    Tweet findById(Long tweetId);

    Tweet updateTweet(Long tweetId, Tweet tweet);

    Tweet deleteTweet(Long tweetId);
}

