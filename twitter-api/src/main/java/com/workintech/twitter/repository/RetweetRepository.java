package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Retweet;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RetweetRepository extends JpaRepository<Retweet,Long> {

    @Query("SELECT r FROM Retweet r WHERE r.user = :user AND r.tweet = :tweet")
    Optional<Retweet> findByUserAndTweet(@Param("user") User user,
                                         @Param("tweet") Tweet tweet);
}
