package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import com.workintech.twitter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    TweetServiceImpl tweetService;

    @Mock
    TweetRepository tweetRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImpl(tweetRepository, userRepository);
    }
    @DisplayName("Can create tweet")
    @Test
    void createTweet() {
        User user = new User();
        user.setId(1L);

        Tweet tweet = new Tweet();
        tweet.setContent("hello");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.save(tweet)).thenReturn(tweet);

        Tweet result = tweetService.createTweet(1L, tweet);

        assertEquals("hello", result.getContent());
        verify(tweetRepository).save(tweet);
    }
    @DisplayName("Can find tweets by user ıd")
    @Test
    void findTweetsByUserId() {
        when(tweetRepository.findByUserId(1L))
                .thenReturn(List.of(new Tweet(), new Tweet()));

        List<Tweet> result = tweetService.findTweetsByUserId(1L);

        assertEquals(2, result.size());
    }
}
