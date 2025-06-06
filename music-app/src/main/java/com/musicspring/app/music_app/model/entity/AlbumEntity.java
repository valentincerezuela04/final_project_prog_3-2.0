package com.musicspring.app.music_app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "albums")

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "spotify_link")
    private String spotifyLink;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "active", nullable = false)
    private Boolean active;

}
