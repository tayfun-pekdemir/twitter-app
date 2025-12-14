package com.workintech.twitter.repository;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    @Query("SELECT l FROM Like l WHERE l.user = :user AND l.tweet = :tweet")
    Optional<Like> findByUserAndTweet(@Param("user") User user, @Param("tweet") Tweet tweet);
}
