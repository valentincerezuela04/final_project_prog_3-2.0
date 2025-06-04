package com.musicspring.app.music_app.spotify.model;

import com.musicspring.app.music_app.model.dto.request.AlbumRequest;
import com.musicspring.app.music_app.model.dto.request.ArtistRequest;
import com.musicspring.app.music_app.model.dto.request.SongRequest;
import lombok.*;
import org.springframework.data.domain.Page;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnifiedSearchResponse {
    private Page<SongRequest> songs;
    private Page<ArtistRequest> artists;
    private Page<AlbumRequest> albums;
    private String query;

    public boolean getHasResults() {
        return (songs != null && !songs.isEmpty()) || 
               (artists != null && !artists.isEmpty()) || 
               (albums != null && !albums.isEmpty());
    }
}
