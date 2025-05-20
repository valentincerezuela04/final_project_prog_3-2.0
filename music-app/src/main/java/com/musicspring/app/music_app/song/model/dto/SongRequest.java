package com.musicspring.app.music_app.song.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Request object containing song details")
public class SongRequest {

    @NotBlank
    @Schema(description = "Spotify unique identifier for the song", example = "3n3Ppam7vgaVa1iaRUc9Lp")
    private String spotifyId;

    @NotBlank
    @Size(max = 200)
    @Schema(description = "Name of the song", example = "Shape of You")
    private String name;

    @NotBlank
    @Size(max = 200)
    @Schema(description = "Name of the artist", example = "Ed Sheeran")
    private String artistName;

    @NotBlank
    @Size(max = 200)
    @Schema(description = "Name of the album the song belongs to", example = "÷ (Divide)")
    private String albumName;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "URL of the album or song cover image", example = "https://i.scdn.co/image/abc123")
    private String imageUrl;

    @NotNull
    @Min(10000)
    @Schema(description = "Duration of the song in milliseconds", example = "233712")
    private Integer durationMs;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "URL to a 30-second song preview", example = "https://p.scdn.co/mp3-preview/abc123?cid=...")
    private String previewUrl;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Full Spotify link to the song", example = "https://open.spotify.com/track/3n3Ppam7vgaVa1iaRUc9Lp")
    private String spotifyLink;

    @NotNull
    @Schema(description = "Release date of the song", example = "2017-01-06")
    private Date releaseDate;

    @Schema(description = "Formatted duration of the song in minutes and seconds (MM:ss)", example = "3:53", accessMode = Schema.AccessMode.READ_ONLY)
    public String getDurationFormatted() {
        if (durationMs == null) return "0:00";

        int totalSeconds = durationMs / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
}
