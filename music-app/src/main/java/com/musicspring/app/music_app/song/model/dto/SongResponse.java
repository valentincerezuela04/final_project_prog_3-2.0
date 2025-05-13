package com.musicspring.app.music_app.song.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SongResponse {

    private Long id;
    private String spotifyId;
    private String name;
    private String artistName;
    private String album;
    private String imageUrl;
    private Integer durationMs;
    private String previewUrl;
    private String spotifyLink;
    private Date releaseDate;

    public String getDurationFormatted() {
        if (durationMs == null) return "0:00";

        int totalSeconds = durationMs / 1000;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d:%02d", minutes, seconds);
    }
}
