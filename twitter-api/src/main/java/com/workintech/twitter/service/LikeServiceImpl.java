package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.DuplicateException;
import com.workintech.twitter.exception.NotAllowedException;
import com.workintech.twitter.exception.NotFoundException;
import com.workintech.twitter.repository.LikeRepository;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private LikeRepository likeRepository;
        @Autowired
        public LikeServiceImpl(TweetRepository tweetRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public Like like(Long tweetId, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found by ID: " + userId)
        );
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(
                () -> new NotFoundException("Tweet not found by ID: " + tweetId)
        );

        if (likeRepository.findByUserAndTweet(user, tweet).isPresent()) {
            throw new DuplicateException("User already liked this tweet");
        }

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        tweet.incrementLikeCount();
        tweetRepository.save(tweet);
        return likeRepository.save(like);
    }

    @Override
    public Like disLike(Long tweetId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found by ID: " + userId)
                );
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(
                        () -> new NotFoundException("Tweet not found by ID: " + tweetId)
                );

        Like existingLike = likeRepository.findByUserAndTweet(user, tweet)
                .orElseThrow(() -> new NotAllowedException("You are not allowed to dislike this tweet"));
        tweet.decrementLikeCount();
        tweetRepository.save(tweet);
        likeRepository.delete(existingLike);
        return existingLike;
    }
}
