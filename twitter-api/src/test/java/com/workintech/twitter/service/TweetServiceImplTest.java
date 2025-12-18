package com.workintech.twitter.service;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    TweetServiceImpl tweetService;

    @Mock
    TweetRepository tweetRepository;

    @Mock
    UserService userService;

    @BeforeEach
    void setUp() {
        tweetService = new TweetServiceImpl(tweetRepository, userService);
    }

    @DisplayName("Can create tweet")
    @Test
    void createTweet() {
        User user = new User();
        user.setId(1L);

        Tweet tweet = new Tweet();
        tweet.setContent("hello");

        // getCurrentUser() mock
        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetRepository.save(tweet)).thenReturn(tweet);

        Tweet result = tweetService.createTweet(tweet);

        assertEquals("hello", result.getContent());
        assertEquals(user, result.getUser());
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

