package com.musicspring.app.music_app.album.model.dto;

import com.musicspring.app.music_app.album.model.entity.EAlbumType;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponse {

    private Long albumId;
    private String spotifyId;
    private EAlbumType albumType;
    private String name;
    private Long artistId;
    private String imageUrl;
    private Integer totalTracks;
    private String previewUrl;
    private String spotifyLink;
    private LocalDate releaseDate;

}
