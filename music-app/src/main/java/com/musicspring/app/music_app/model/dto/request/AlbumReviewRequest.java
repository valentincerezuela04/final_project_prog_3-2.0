package com.musicspring.app.music_app.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Request payload for creating or updating an album review",
        requiredProperties = {"userId", "albumId", "rating", "description"})
public class AlbumReviewRequest {

    @Schema(description = "ID of the user submitting the review", example = "123")
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(description = "ID of the album being reviewed", example = "456")
    @NotNull(message = "Album ID is required")
    private Long albumId;

    @Schema(description = "Rating given to the album, between 0.5 and 5.0")
    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.5", message = "Rating must be at least 0.5")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    @Digits(integer = 1, fraction = 2, message = "Rating format is invalid")
    private Double rating;

    @Schema(description = "Description of the review, max 500 characters", example = "Great album with amazing tracks!")
    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}
