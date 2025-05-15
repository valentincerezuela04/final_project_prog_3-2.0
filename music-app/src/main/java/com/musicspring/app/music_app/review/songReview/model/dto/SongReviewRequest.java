package com.musicspring.app.music_app.review.songReview.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SongReviewRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long songId;
    @NotBlank
    @DecimalMin(value = "0.5")
    @DecimalMax(value = "5.0")
    @Digits(integer = 1,fraction = 2)
    private Double rating;
    @NotBlank
    @Size(max = 500)
    private String description;
}
