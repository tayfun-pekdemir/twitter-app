package com.workintech.twitter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long userId;
    private Long tweetId;

    @NotBlank
    @Size(min = 1, max = 280)
    private String content;
}
