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

    @Schema(description = "ID of the album being reviewed (for existing songs in database)", example = "456")
    @NotNull(message = "Album ID is required")
    private Long albumId;

    @Schema(description = "Spotify ID of the album being reviewed (for new albums from Spotify)",
            example = "7pFZ24pG0mosFU3jnwMZbz")
    private String spotifyId;

    @Schema(description = "Name of the album (required when using spotifyId)",
            example = "Ill communication")
    private String albumName;

    @Schema(description = "Name of the artist (required when using spotifyId)",
            example = "Beastie Boys")
    private String artistName;

    @Schema(description = "URL of the album's image (required when using spotifyId)",
            example = "https://i.scdn.co/image/ab67616d0000b273e1cd962a266e720dfeb77b6f")
    private String imageUrl;

    @Schema(description = "Spotify link to the album",
            example = "https://open.spotify.com/intl-es/album/7pFZ24pG0mosFU3jnwMZbz")
    private String spotifyLink;

    @Schema(description = "Release date of the album",
            example = "1994-05-23")
    private String releaseDate;

    @Schema(description = "Rating given to the album, between 0.5 and 5.0",
            example = "4.5")
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
