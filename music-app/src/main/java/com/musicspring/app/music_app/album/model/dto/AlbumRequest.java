package com.musicspring.app.music_app.album.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
            example = "3KuXEGcqLcnEYWnn3OEGy0")
    private String spotifyId;

    @NotBlank(message = "Title can not be empty.")
    @Schema(description = "Title of the album",
            example = "Future Nostalgia")
    private String title;

    @NotNull(message = "Artist ID can not be empty")
    @Schema(description = "ID of the artist associated with the album",
            example = "101")
    private Long artistId;

    @NotNull(message = "Release date can not be null.")
    @Schema(description = "Release date of the album in ISO format (yyyy-MM-dd)",
            example = "2020-03-27", type = "string", format = "date")
    private LocalDate releaseDate;
}
