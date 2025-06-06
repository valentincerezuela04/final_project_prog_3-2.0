package com.musicspring.app.music_app.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request payload for creating or updating an album",
        requiredProperties = {"spotifyId", "title", "artistId", "releaseDate"})
public class AlbumRequest {

    @NotBlank(message = "Spotify ID can not be empty.")
    @Schema(description = "Spotify ID of the album",
            example = "7pFZ24pG0mosFU3jnwMZbz")
    private String spotifyId;

    @NotBlank(message = "Title can not be empty.")
    @Schema(description = "Title of the album",
            example = "Ill Communication")
    private String title;

    @NotBlank(message = "Artist name can not be empty.")
    @Schema(description = "Name of the artist", example = "Beastie Boys")
    private String artistName;

    @Size(max = 255, message = "Image URL must be at most 255 characters.")
    @Schema(description = "URL of the album cover image", example = "https://i.scdn.co/image/ab67616d0000b273e1cd962a266e720dfeb77b6f")
    private String imageUrl;

    @Size(max = 255, message = "Spotify link must be at most 255 characters.")
    @Schema(description = "Full Spotify link to the album", example = "https://open.spotify.com/intl-es/album/7pFZ24pG0mosFU3jnwMZbz")
    private String spotifyLink;

    @NotNull(message = "Release date can not be null.")
    @PastOrPresent(message = "Release date cannot be in the future.")
    @Schema(description = "Release date of the album in ISO format (yyyy-MM-dd)",
            example = "1994-05-23", type = "string", format = "date")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Limit must be at least 1.")
    @Max(value = 100, message = "Limit must be at most 100.")
    @Schema(description = "Maximum number of results to return", example = "10")
    private Integer limit;

    @Min(value = 0, message = "Offset must be zero or positive.")
    @Schema(description = "Result page offset", example = "0")
    private Integer offset;

    @Schema(description = "Sort results by specific field", example = "popularity")
    private String sortBy;

}
