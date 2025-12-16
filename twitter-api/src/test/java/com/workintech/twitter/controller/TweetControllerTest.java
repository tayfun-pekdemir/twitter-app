package com.workintech.twitter.controller;

import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.TweetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
@AutoConfigureMockMvc
class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TweetService tweetService;

    @DisplayName("Can create tweet with POST request and returns 201 CREATED")
    @Test
    @WithMockUser(username = "user")
    void createTweet() throws Exception {


        User user = new User();
        user.setId(1L);

        Tweet savedTweet = new Tweet();
        savedTweet.setId(10L);
        savedTweet.setContent("hello");
        savedTweet.setUser(user);


        when(tweetService.createTweet(eq(1L), any(Tweet.class)))
                .thenReturn(savedTweet);

        mockMvc.perform(post("/tweet")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "userId": 1,
                                  "content": "hello"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tweetId").value(10))
                .andExpect(jsonPath("$.content").value("hello"))
                .andExpect(jsonPath("$.userId").value(1));
    }
}


