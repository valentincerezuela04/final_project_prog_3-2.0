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

    public List<SongResponse> toResponseList(List<SongEntity> songs) {
        if (songs == null) return null;

        return songs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
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

    public SongRequest toRequest(SongEntity song){
        if(song == null) return null;

        return SongRequest.builder()
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

    public SongRequest toRequestFromReviewRequest(SongReviewRequest reviewRequest) {
        if (reviewRequest == null) return null;

        // Convert release date string to Date if provided
        Date releaseDate = null;
        if (reviewRequest.getReleaseDate() != null && !reviewRequest.getReleaseDate().isEmpty()) {
            releaseDate = java.sql.Date.valueOf(reviewRequest.getReleaseDate());
        }

        return SongRequest.builder()
                .spotifyId(reviewRequest.getSpotifyId())
                .name(reviewRequest.getSongName())
                .artistName(reviewRequest.getArtistName())
                .albumName(reviewRequest.getAlbumName())
                .imageUrl(reviewRequest.getImageUrl())
                .durationMs(reviewRequest.getDurationMs())
                .previewUrl(reviewRequest.getPreviewUrl())
                .spotifyLink(reviewRequest.getSpotifyLink())
                .releaseDate(releaseDate)
                .build();
    }

}
