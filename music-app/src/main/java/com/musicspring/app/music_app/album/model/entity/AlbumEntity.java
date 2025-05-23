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
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "Album ID can not be empty.")
    @Positive
    private Long albumId;

    @Column(name = "spotify_id")
    @NotBlank(message = "Spotify ID can not be empty.")
    private String spotifyId;

    @Column(name = "title")
    @NotBlank(message = "Title can not be empty.")
    private String title;

    @Column(name = "release_date")
    @NotNull(message = "Release date can not be empty.")
    private LocalDate releaseDate;


    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "active", nullable = false)
    @NotNull(message = "Active can not be empty.")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private ArtistEntity artist;
}
