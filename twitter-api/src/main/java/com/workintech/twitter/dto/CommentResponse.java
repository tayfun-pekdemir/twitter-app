package com.workintech.twitter.dto;

public record CommentResponse(
        Long commentId,
        String content,
        Long userId,
        Long tweetId
) {}
