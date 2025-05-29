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
        requiredProperties = {"userId", "rating", "description"})
public class SongReviewRequest {

    @NotNull
    @Schema(description = "ID of the user who created the review",
            example = "789")
    private Long userId;
    
    @Schema(description = "ID of the song being reviewed (for existing songs in database)",
            example = "456")
    private Long songId;

    @Schema(description = "Spotify ID of the song being reviewed (for new songs from Spotify)",
            example = "4iV5W9uYEdYUVa79Axb7Rh")
    private String spotifyId;

    // Song data fields (used when creating new songs from Spotify)
    @Schema(description = "Name of the song (required when using spotifyId)",
            example = "Bohemian Rhapsody")
    private String songName;

    @Schema(description = "Name of the artist (required when using spotifyId)",
            example = "Queen")
    private String artistName;

    @Schema(description = "Name of the album (required when using spotifyId)",
            example = "A Night at the Opera")
    private String albumName;

    @Schema(description = "URL of the song's image (required when using spotifyId)",
            example = "https://i.scdn.co/image/ab67616d0000b273e319baafd16e84f0408af2a0")
    private String imageUrl;

    @Schema(description = "Duration of the song in milliseconds (required when using spotifyId)",
            example = "354000")
    private Integer durationMs;

    @Schema(description = "Preview URL of the song",
            example = "https://p.scdn.co/mp3-preview/...")
    private String previewUrl;

    @Schema(description = "Spotify link to the song",
            example = "https://open.spotify.com/track/4iV5W9uYEdYUVa79Axb7Rh")
    private String spotifyLink;

    @Schema(description = "Release date of the song",
            example = "1975-10-31")
    private String releaseDate;

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
