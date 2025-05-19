package com.musicspring.app.music_app.song.model.mapper;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongMapper {

    public SongResponse toResponse(SongEntity song) {
        if (song == null) {
            return null;
        }

        return SongResponse.builder()
                .id(song.getSongId())
                .spotifyId(song.getSpotifyId())
                .name(song.getName())
                .artistName(song.getArtistName())
                .album(song.getAlbum())
                .imageUrl(song.getImageUrl())
                .durationMs(song.getDurationMs())
                .previewUrl(song.getPreviewUrl())
                .spotifyLink(song.getSpotifyLink())
                .releaseDate(song.getReleaseDate())
                .build();
    }

    public List<SongResponse> toResponseList(List<SongEntity> songs) {
        if (songs == null) {
            return null;
        }
        return songs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<SongResponse> toResponsePage(Page<SongEntity> songEntityPage){
        if(songEntityPage == null){
            return Page.empty();
        }
        return songEntityPage.map(this::toResponse);
    }



}
