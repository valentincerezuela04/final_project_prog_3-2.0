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
public class AlbumRequest {
    @NotBlank(message = "Spotify ID can not be empty.")
    private String spotifyId;
    @Schema(name =  "title" , description = "The title of the album" ,example = "album")
    @NotBlank(message = "Title can not be empty.")
    private String title;

    @NotNull(message = "Artist ID can not be empty")
    private Long artistId;

    @NotNull(message = "Release date can not be null.")
    private LocalDate releaseDate;



}
