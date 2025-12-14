package com.workintech.twitter.controller;

import com.workintech.twitter.dto.DeleteTweetRequest;
import com.workintech.twitter.dto.TweetRequest;
import com.workintech.twitter.dto.TweetResponse;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public TweetResponse createTweet(@Valid @RequestBody TweetRequest request) {
        Tweet tweet = new Tweet();
        tweet.setContent(request.getContent());

        Tweet savedTweet = tweetService.createTweet(request.getUserId(), tweet);

        return new TweetResponse(
                savedTweet.getId(),
                savedTweet.getContent(),
                savedTweet.getUser().getId()
        );
    }

    @GetMapping("/findByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponse> findTweetsByUserId(@PathVariable Long userId) {
        List<Tweet> tweets = tweetService.findTweetsByUserId(userId);
        List<TweetResponse> responseList = new ArrayList<>();

        for (Tweet tweet : tweets) {
            responseList.add(new TweetResponse(
                    tweet.getId(),
                    tweet.getContent(),
                    tweet.getUser().getId()
            ));
        }
        return responseList;
    }

    @GetMapping("/findById/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse findById(@PathVariable Long tweetId) {
        Tweet tweet = tweetService.findById(tweetId);
        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                tweet.getUser().getId()
        );
    }

    @PutMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse updateTweet(@PathVariable Long tweetId,
                                     @Valid @RequestBody TweetRequest request) {
        Tweet tweet = new Tweet();
        tweet.setContent(request.getContent());

        Tweet updatedTweet = tweetService.updateTweet(tweetId, request.getUserId(), tweet);

        return new TweetResponse(
                updatedTweet.getId(),
                updatedTweet.getContent(),
                updatedTweet.getUser().getId()
        );
    }

    @DeleteMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.OK)
    public TweetResponse deleteTweet(@PathVariable Long tweetId,
                                     @Valid @RequestBody DeleteTweetRequest request) {
        Tweet deletedTweet = tweetService.deleteTweet(tweetId, request.getUserId());

        return new TweetResponse(
                deletedTweet.getId(),
                deletedTweet.getContent(),
                deletedTweet.getUser().getId()
        );
    }
}
