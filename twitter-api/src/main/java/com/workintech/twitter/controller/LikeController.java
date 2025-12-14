package com.workintech.twitter.controller;

import com.workintech.twitter.dto.LikeRequest;
import com.workintech.twitter.dto.LikeResponse;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse like(@Valid @RequestBody LikeRequest request) {
        Like like = likeService.like(request.getTweetId(), request.getUserId());
        return new LikeResponse(like.getId(), like.getUser().getId(), like.getTweet().getId());
    }

    @PostMapping("/dislike")
    @ResponseStatus(HttpStatus.OK)
    public LikeResponse dislike(@Valid @RequestBody LikeRequest request) {
        Like like = likeService.disLike(request.getTweetId(), request.getUserId());
        return new LikeResponse(like.getId(), like.getUser().getId(), like.getTweet().getId());
    }
}
