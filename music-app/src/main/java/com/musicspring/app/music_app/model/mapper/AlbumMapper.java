package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.AlbumRequest;
import com.musicspring.app.music_app.model.dto.AlbumResponse;
import com.musicspring.app.music_app.model.entity.AlbumEntity;
import com.musicspring.app.music_app.model.entity.ArtistEntity;
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
                    .albumId(album.getId())
                    .spotifyId(album.getSpotifyId())
                    .title(album.getTitle())
                    .artistId(album.getArtist().getArtistId())
                    .releaseDate(album.getReleaseDate())
                    .imageUrl(album.getImageUrl())
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
                .id(albumResponse.getAlbumId())
                .spotifyId(albumResponse.getSpotifyId())
                .title(albumResponse.getTitle())
                .releaseDate(albumResponse.getReleaseDate())
                .imageUrl(albumResponse.getImageUrl())
                .active(true)
                .artist(artistEntity)
                .build();
    }

    public AlbumEntity requestToEntity (AlbumRequest albumRequest, ArtistEntity artistEntity){
        return AlbumEntity.builder()
                .spotifyId(albumRequest.getSpotifyId())
                .title(albumRequest.getTitle())
                .releaseDate(albumRequest.getReleaseDate())
                .imageUrl(albumRequest.getImageUrl())
                .artist(artistEntity)
                .active(true)
                .build();
    }
}

