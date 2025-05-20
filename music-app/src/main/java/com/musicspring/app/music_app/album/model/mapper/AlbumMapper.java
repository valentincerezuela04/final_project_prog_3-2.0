package com.musicspring.app.music_app.album.model.mapper;

import com.musicspring.app.music_app.album.model.dto.AlbumRequest;
import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumMapper {
    public AlbumResponse toResponse(AlbumEntity album){
        if(album==null){
            return null;
        }else{
            return AlbumResponse.builder()
                    .albumId(album.getAlbumId())
                    .spotifyId(album.getSpotifyId())
                    .title(album.getTitle())
                    .artistId(album.getArtist().getArtistId())
                    .releaseDate(album.getReleaseDate())
                    .build();
        }
    }

    public List<AlbumResponse> toResponseList (List<AlbumEntity> albums){
        if(albums == null){
            return  null;
        }
        return albums.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<AlbumEntity> toEntityPage(List<AlbumEntity> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<AlbumEntity> sublist = list.subList(start, end);
        return new PageImpl<>(sublist, pageable, list.size());
    }


    public Page<AlbumResponse> toResponsePage (Page<AlbumEntity> albumEntityPage){
        if(albumEntityPage == null){
            return  Page.empty();
        }
        return albumEntityPage.map(this::toResponse);
    }

    public AlbumEntity responseToEntity (AlbumResponse albumResponse, ArtistEntity artistEntity){
        return AlbumEntity.builder()
                .albumId(albumResponse.getAlbumId())
                .spotifyId(albumResponse.getSpotifyId())
                .title(albumResponse.getTitle())
                .releaseDate(albumResponse.getReleaseDate())
                .active(true)
                .artist(artistEntity)
                .build();
    }

    public AlbumEntity requestToEntity (AlbumRequest albumRequest, ArtistEntity artistEntity){
        return AlbumEntity.builder()
                .spotifyId(albumRequest.getSpotifyId())
                .title(albumRequest.getTitle())
                .releaseDate(albumRequest.getReleaseDate())
                .artist(artistEntity)
                .active(true)
                .build();
    }
}

