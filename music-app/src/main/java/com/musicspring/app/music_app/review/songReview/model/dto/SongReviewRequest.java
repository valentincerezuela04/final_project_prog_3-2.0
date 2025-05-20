package com.musicspring.app.music_app.review.songReview.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Request payload for creating or updating a song review",
        requiredProperties = {"userId", "songId", "rating", "description"})
public class SongReviewRequest {

    @NotNull
    @Schema(description = "ID of the user who created the review",
            example = "789")
    private Long userId;

    @NotNull
    @Schema(description = "ID of the song being reviewed",
            example = "456")
    private Long songId;

    @NotNull
    @DecimalMin(value = "0.5", message = "Rating must be at least 0.5")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    @Digits(integer = 1, fraction = 2)
    @Schema(description = "Rating given to the song (between 0.5 and 5.0)",
            example = "4.5")
    private Double rating;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Textual description of the review",
            example = "Great song with amazing vocals!", maxLength = 500)
    private String description;

}
