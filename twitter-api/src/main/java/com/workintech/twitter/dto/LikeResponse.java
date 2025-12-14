package com.workintech.twitter.dto;

public record LikeResponse(
        Long likeId,
        Long userId,
        Long tweetId
) {}
