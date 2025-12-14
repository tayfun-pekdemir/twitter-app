package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tweet createTweet(@RequestParam Long userId, @RequestBody Tweet tweet){
        return tweetService.createTweet(userId,tweet);
    }

    @GetMapping("/findByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Tweet> findTweetsByUserId(@PathVariable Long userId){
        return tweetService.findTweetsByUserId(userId);
    }

    @GetMapping("/findById/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet findById(@PathVariable Long tweetId){
        return tweetService.findById(tweetId);
    }

    @PutMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet updateTweet(@PathVariable Long tweetId,@RequestParam Long userId,@RequestBody Tweet tweet){
        return tweetService.updateTweet(tweetId,userId,tweet);
    }

    @DeleteMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public Tweet deleteTweet(@PathVariable Long tweetId,@RequestParam Long userId){
        return tweetService.deleteTweet(tweetId,userId);
    }
}
