package com.musicspring.app.music_app.song.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for song operations")
public class SongRequest {

    @Schema(description = "Spotify ID of the song", example = "3n3Ppam7vgaVa1iaRUc9Lp")
    private String spotifyId;

    @Schema(description = "Name of the song", example = "Shape of You")
    private String name;

    @Schema(description = "Name of the artist", example = "Ed Sheeran")
    private String artistName;

    @Schema(description = "Name of the album", example = "÷ (Divide)")
    private String albumName;

    @Schema(description = "URL of the album or song cover image", example = "https://i.scdn.co/image/abc123")
    private String imageUrl;

    @Schema(description = "Duration of the song in milliseconds", example = "233712")
    private Integer durationMs;

    @Schema(description = "URL to a 30-second song preview", example = "https://p.scdn.co/mp3-preview/abc123")
    private String previewUrl;

    @Schema(description = "Full Spotify link to the song", example = "https://open.spotify.com/track/3n3Ppam7vgaVa1iaRUc9Lp")
    private String spotifyLink;

    @Schema(description = "Release date of the song", example = "2017-01-06")
    private Date releaseDate;

    @Schema(description = "Maximum number of results to return", example = "10")
    private Integer limit;

    @Schema(description = "Result page offset", example = "0")
    private Integer offset;

    @Schema(description = "Sort results by specific field", example = "popularity")
    private String sortBy;
}
