package com.workintech.twitter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @Column(name="last_name")
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @Column(name="user_name", unique = true)
    @NotBlank
    @Size(min=4,max = 30)
    private String userName;

    @Column(name="email", unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(name="password")
    @NotBlank
    @Size(min = 6)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Retweet> retweets = new ArrayList<>();
}
