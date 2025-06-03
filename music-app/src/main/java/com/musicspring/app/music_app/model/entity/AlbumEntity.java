package com.musicspring.app.music_app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "albums")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "spotify_id")
    private String spotifyId;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;
}
