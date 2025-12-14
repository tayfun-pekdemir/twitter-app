package com.workintech.twitter.controller;

import com.workintech.twitter.dto.DeleteRetweetRequest;
import com.workintech.twitter.dto.RetweetRequest;
import com.workintech.twitter.dto.RetweetResponse;
import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.service.RetweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private RetweetService retweetService;

    @Autowired
    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RetweetResponse retweet(@Valid @RequestBody RetweetRequest request){
        Retweet retweet = retweetService.retweet(request.getUserId(), request.getTweetId());
        return new RetweetResponse(retweet.getId(), retweet.getUser().getId(), retweet.getTweet().getId());
    }

    @DeleteMapping("/{retweetId}")
    @ResponseStatus(HttpStatus.OK)
    public RetweetResponse deleteRetweet(@PathVariable Long retweetId,
                                         @Valid @RequestBody DeleteRetweetRequest request) {
        Retweet retweet = retweetService.deleteRetweet(retweetId, request.getUserId());
        return new RetweetResponse(retweet.getId(), retweet.getUser().getId(), retweet.getTweet().getId());
    }
}
