package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.request.SongReviewRequest;
import com.musicspring.app.music_app.model.dto.request.SongRequest;
import com.musicspring.app.music_app.model.dto.response.SongResponse;
import com.musicspring.app.music_app.model.entity.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class SongMapper {

    public SongResponse toResponse(SongEntity song) {
        return SongResponse.builder()
                .songId(song.getSongId())
                .spotifyId(song.getSpotifyId())
                .name(song.getName())
                .artistName(song.getArtistName())
                .albumName(song.getAlbumName())
                .imageUrl(song.getImageUrl())
                .durationMs(song.getDurationMs())
                .previewUrl(song.getPreviewUrl())
                .spotifyLink(song.getSpotifyLink())
                .releaseDate(song.getReleaseDate())
                .build();
    }



    public Page<SongResponse> toResponsePage(Page<SongEntity> songEntityPage){
        return songEntityPage.map(this::toResponse);
    }

    public SongEntity toEntity(SongRequest song){
        return SongEntity.builder()
                .spotifyId(song.getSpotifyId())
                .name(song.getName())
                .artistName(song.getArtistName())
                .albumName(song.getAlbumName())
                .imageUrl(song.getImageUrl())
                .durationMs(song.getDurationMs())
                .previewUrl(song.getPreviewUrl())
                .spotifyLink(song.getSpotifyLink())
                .releaseDate(song.getReleaseDate())
                .active(true)
                .build();
    }

    public SongEntity toEntity(SongResponse song){
        return SongEntity.builder()
                .spotifyId(song.getSpotifyId())
                .name(song.getName())
                .artistName(song.getArtistName())
                .albumName(song.getAlbumName())
                .imageUrl(song.getImageUrl())
                .durationMs(song.getDurationMs())
                .previewUrl(song.getPreviewUrl())
                .spotifyLink(song.getSpotifyLink())
                .releaseDate(song.getReleaseDate())
                .active(true)
                .build();
    }

    public SongEntity toEntityFromReview (SongReviewRequest songReviewRequest, LocalDate releaseDate) {
        return SongEntity.builder()
                .spotifyId(songReviewRequest.getSpotifyId())
                .name(songReviewRequest.getSongName())
                .artistName(songReviewRequest.getArtistName())
                .albumName(songReviewRequest.getAlbumName())
                .imageUrl(songReviewRequest.getImageUrl())
                .durationMs(songReviewRequest.getDurationMs())
                .previewUrl(songReviewRequest.getPreviewUrl())
                .spotifyLink(songReviewRequest.getSpotifyLink())
                .releaseDate(releaseDate)
                .active(true)
                .build();
    }

}
