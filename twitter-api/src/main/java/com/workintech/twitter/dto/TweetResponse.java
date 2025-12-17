package com.workintech.twitter.dto;

public record TweetResponse(
        Long tweetId,
        String content,
        UserDTO user,
        int likeCount,
        int retweetCount,
        int commentCount
) {}
