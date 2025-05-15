package com.musicspring.app.music_app.album.model.entity;

import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    private Long albumId;

    @Column(name = "spotify_id")
    @NotBlank(message = "Spotify ID can not be empty.")
    private String spotifyId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EAlbumType albumType;

    @Column(name = "name")
    @NotBlank(message = "Name can not be empty.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "total_tracks")
    @NotNull(message = "Total tracks can not be empty.")
    @Positive
    private Integer totalTracks;

    @Column(name = "preview_url")
    private String previewUrl;

    @Column(name = "spotify_link")
    private String spotifyLink;

    @Column(name = "release_date")
    @NotNull(message = "Release date can not be empty.")
    private LocalDate releaseDate;

    @Column(name = "active", nullable = false)
    @NotNull(message = "Active can not be empty.")
    private Boolean active;


}
