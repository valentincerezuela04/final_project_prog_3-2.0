package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.request.AlbumRequest;
import com.musicspring.app.music_app.model.dto.request.AlbumReviewRequest;
import com.musicspring.app.music_app.model.dto.response.AlbumResponse;
import com.musicspring.app.music_app.model.entity.AlbumEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumMapper {
    public AlbumResponse toResponse(AlbumEntity album){
        return AlbumResponse.builder()
                .albumId(album.getAlbumId())
                .spotifyId(album.getSpotifyId())
                .title(album.getTitle())
                .artistName(album.getArtistName())
                .imageUrl(album.getImageUrl())
                .spotifyLink(album.getSpotifyLink())
                .releaseDate(album.getReleaseDate())
                .build();
    }

    public List<AlbumResponse> toResponseList (List<AlbumEntity> albums){
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
        return albumEntityPage.map(this::toResponse);
    }

    public AlbumEntity responseToEntity (AlbumResponse albumResponse){
        return AlbumEntity.builder()
                .albumId(albumResponse.getAlbumId())
                .spotifyId(albumResponse.getSpotifyId())
                .title(albumResponse.getTitle())
                .artistName(albumResponse.getArtistName())
                .imageUrl(albumResponse.getImageUrl())
                .spotifyLink(albumResponse.getSpotifyLink())
                .releaseDate(albumResponse.getReleaseDate())
                .active(true)
                .build();
    }

    public AlbumEntity requestToEntity (AlbumRequest albumRequest){
        return AlbumEntity.builder()
                .spotifyId(albumRequest.getSpotifyId())
                .title(albumRequest.getTitle())
                .artistName(albumRequest.getArtistName())
                .imageUrl(albumRequest.getImageUrl())
                .spotifyLink(albumRequest.getSpotifyLink())
                .releaseDate(albumRequest.getReleaseDate())
                .active(true)
                .build();
    }

    public AlbumEntity toEntityFromReview (AlbumReviewRequest albumReviewRequest, LocalDate releaseDate) {
        return AlbumEntity.builder()
                .spotifyId(albumReviewRequest.getSpotifyId())
                .title(albumReviewRequest.getAlbumName())
                .artistName(albumReviewRequest.getArtistName())
                .imageUrl(albumReviewRequest.getImageUrl())
                .spotifyLink(albumReviewRequest.getSpotifyLink())
                .releaseDate(releaseDate)
                .active(true)
                .build();
    }
}

