package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.service.RetweetService;
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
    public Retweet retweet(@RequestParam Long userId,@RequestParam Long tweetId){
        return retweetService.retweet(userId,tweetId);
    }

    @DeleteMapping("/{retweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Retweet deleteRetweet(@PathVariable Long retweetId,@RequestParam Long userId){
        return retweetService.deleteRetweet(retweetId,userId);
    }
}
