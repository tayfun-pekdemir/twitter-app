package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tweet")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    @NotBlank
    @Size(max = 280)
    private String content;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "retweet_count", nullable = false)
    private int retweetCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets = new ArrayList<>();

    public void incrementCommentCount(){
        commentCount = commentCount+1;
    }

    public void decrementCommentCount(){
       if(commentCount>0){
           commentCount = commentCount-1;
       }
    }

    public void incrementRetweetCount(){
        retweetCount = retweetCount+1;
    }

    public void decrementRetweetCount(){
        if(retweetCount>0){
            retweetCount = retweetCount-1;
        }
    }

    public void incrementLikeCount(){
        likeCount = likeCount+1;
    }

    public void decrementLikeCount(){
        if(likeCount>0){
            likeCount = likeCount-1;
        }
    }
}
