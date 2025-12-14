package com.workintech.twitter.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteTweetRequest {
    @NotNull
    private Long userId;
}
