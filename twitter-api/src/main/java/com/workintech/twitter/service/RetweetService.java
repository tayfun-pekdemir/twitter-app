package com.workintech.twitter.service;

import com.workintech.twitter.entity.Retweet;

public interface RetweetService {

    Retweet retweet(Long tweetId);
    Retweet deleteRetweet(Long retweetId);
}
