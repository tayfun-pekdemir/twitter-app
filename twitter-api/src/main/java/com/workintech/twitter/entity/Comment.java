package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",nullable = false)
    @NotBlank
    @Size(max = 280)
    private String content;

    @ManyToOne
    @JoinColumn(name = "tweet_id",nullable = false)
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
