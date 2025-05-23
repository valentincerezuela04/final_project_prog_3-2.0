package com.musicspring.app.music_app.artist.model.dto;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistWithSongsResponse {
    private Long artistId;
    private String name;
    private Integer followers;
    private String imageUrl;
    private List<SongResponse> songs;

}
