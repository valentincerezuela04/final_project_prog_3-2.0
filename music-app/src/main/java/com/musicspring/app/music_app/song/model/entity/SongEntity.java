package com.musicspring.app.music_app.song.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "songs")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @Column(name = "spotify_id", unique = true, nullable = false)
    private String spotifyId;

    @Column(nullable = false)
    private String name;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "duration_ms")
    private Integer durationMs;

    @Column(name = "preview_url")
    private String previewUrl;

    @Column(name = "spotify_link")
    private String spotifyLink;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(nullable = false)
    private boolean active;

}
