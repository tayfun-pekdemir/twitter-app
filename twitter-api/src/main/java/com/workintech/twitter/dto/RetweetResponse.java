package com.workintech.twitter.dto;

public record RetweetResponse(
        Long retweetId,
        Long userId,
        Long tweetId
) {}
