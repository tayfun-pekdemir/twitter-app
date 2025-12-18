package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;

public interface LikeService {

    Like like(Long tweetId);
    Like disLike(Long tweetId);
}
