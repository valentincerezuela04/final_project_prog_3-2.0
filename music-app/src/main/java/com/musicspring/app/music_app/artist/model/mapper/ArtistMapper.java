package com.musicspring.app.music_app.artist.model.mapper;

import com.musicspring.app.music_app.artist.model.dto.ArtistRequest;
import com.musicspring.app.music_app.artist.model.dto.ArtistResponse;
import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistMapper {

    @Autowired
    private SongMapper songMapper;

    public ArtistResponse toResponse(ArtistEntity entity) {
        if (entity == null) {
            return null;
        }

        return ArtistResponse.builder()
                .artistId(entity.getArtistId())
                .name(entity.getName())
                .followers(entity.getFollowers())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    public List<ArtistResponse> toResponseList(List<ArtistEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ArtistWithSongsResponse toArtistWithSongsResponse(ArtistEntity artist) {
        if (artist == null) {
            return null;
        }

        List<SongResponse> songs = artist.getSongs().stream()
                .map(songMapper::toResponse)
                .collect(Collectors.toList());

        return ArtistWithSongsResponse.builder()
                .artistId(artist.getArtistId())
                .name(artist.getName())
                .followers(artist.getFollowers())
                .imageUrl(artist.getImageUrl())
                .songs(songs)
                .build();
    }

    public ArtistEntity toEntity(ArtistRequest request) {
        if (request == null) {
            return null;
        }

        return ArtistEntity.builder()
                .name(request.getName())
                .followers(request.getFollowers())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public ArtistEntity toEntityResponse(ArtistResponse response){
        if (response == null) {
            return null;
        }

        return ArtistEntity.builder()
                .name(response.getName())
                .followers(response.getFollowers())
                .imageUrl(response.getImageUrl())
                .build();
    }
    }

