package com.musicspring.app.music_app.song.model.mapper;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.song.model.dto.SongRequest;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongMapper {

    public SongResponse toResponse(SongEntity song) {
        if (song == null) return null;

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
        if(songEntityPage == null) return Page.empty();

        return songEntityPage.map(this::toResponse);
    }

    public SongEntity toEntity(SongRequest song){
        if(song == null) return null;

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
        if(song == null) return null;

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

    public SongEntity toEntityFromReview (SongReviewRequest songReviewRequest) {
        if(songReviewRequest == null) return null;
        // Convert release date string to Date if provided
        Date releaseDate = null;
        if (songReviewRequest.getReleaseDate() != null) {
            releaseDate = java.sql.Date.valueOf(songReviewRequest.getReleaseDate());
        }
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
