package com.workintech.twitter.service;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
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

        User user = userRepository.findById(userId).orElseThrow(/*Exception*/);
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(/*Exception*/);

        Like like = new Like();
        like.setUser(user);
        like.setTweet(tweet);
        return likeRepository.save(like);
    }

    @Override
    public Like disLike(Long tweetId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(/*Exception*/);
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(/*Exception*/);

        Like existingLike = likeRepository.findByUserAndTweet(user, tweet).orElse(null);

        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return existingLike;
        } else {
            return null;
        }
    }
}
