package com.workintech.twitter.dto;

public record TweetResponse(
        Long tweetId,
        String content,
        Long userId
) {}
