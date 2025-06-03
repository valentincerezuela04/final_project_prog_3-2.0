package com.musicspring.app.music_app.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
            example = "Ill Comunication")
    private String title;

    @NotNull(message = "Artist ID can not be empty")
    @Schema(description = "ID of the artist associated with the album",
            example = "101")
    private Long artistId;

    @Schema(description = "URL of the album's cover image",
            example = "https://i.scdn.co/image/ab67616d0000b273e5d7e3a7f93b4e2e7e8b2b53")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Release date can not be null.")
    @Schema(description = "Release date of the album in ISO format (yyyy-MM-dd)",
            example = "1994-05-31", type = "string", format = "date")
    private LocalDate releaseDate;
}
