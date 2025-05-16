package com.musicspring.app.music_app.artist.model.mapper;

import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongId;
import com.musicspring.app.music_app.song.model.entity.SongEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistXSongMapper {

    public ArtistXSongResponse toResponse(ArtistXSongEntity entity) {
        if (entity == null) {
            return null;
        }

        return ArtistXSongResponse.builder()
                .artistId(entity.getArtistXSongId().getArtistId())
                .songId(entity.getArtistXSongId().getSongId())
                .build();
    }

    public List<ArtistXSongResponse> toResponseList(List<ArtistXSongEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ArtistXSongEntity toEntity(Long artistId, Long songId, ArtistEntity artist, SongEntity song) {
        if (artistId == null || songId == null || artist == null || song == null) {
            return null;
        }

        ArtistXSongId id = new ArtistXSongId(artistId, songId);

        return ArtistXSongEntity.builder()
                .artistXSongId(id)
                .artist(artist)
                .song(song)
                .build();
    }
}