package com.musicspring.app.music_app.album.model.mapper;

import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
                    .albumType(album.getAlbumType())
                    .name(album.getName())
                    .artistId(album.getArtist().getArtistId())
                    .imageUrl(album.getImageUrl())
                    .totalTracks(album.getTotalTracks())
                    .previewUrl(album.getPreviewUrl())
                    .spotifyLink(album.getSpotifyLink())
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

    public Page<AlbumResponse> toResponsePage (Page<AlbumEntity> albums){
        if(albums == null){
            return  Page.empty();
        }
        return albums.map(this::toResponse);
    }
}
