package com.workintech.twitter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RetweetRequest {

    @NotNull
    private Long tweetId;
}
