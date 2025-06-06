package com.musicspring.app.music_app.model.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponse {
    @NotBlank(message = "Album ID can not be empty.")
    @Positive
    private Long albumId;

    @NotBlank(message = "Spotify ID can not be empty.")
    private String spotifyId;

    @NotBlank(message = "Title can not be empty.")
    private String title;

    @NotNull(message = "Artist name can not be empty")
    private String artistName;

    private String imageUrl;

    @NotBlank(message = "Spotify link can not be empty.")
    private String spotifyLink;

    @NotNull(message = "Release date can not be null.")
    private LocalDate releaseDate;

}
