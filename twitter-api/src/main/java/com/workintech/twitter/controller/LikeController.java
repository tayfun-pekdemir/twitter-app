package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    private LikeService likeService;
    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.CREATED)
    public Like like(@RequestParam Long tweetId,@RequestParam Long userId){
        return likeService.like(tweetId,userId);
    };

    @PostMapping("/dislike")
    @ResponseStatus(HttpStatus.OK)
    public Like disLike(@RequestParam Long tweetId,@RequestParam Long userId){
        return likeService.disLike(tweetId,userId) ;
    }

}

