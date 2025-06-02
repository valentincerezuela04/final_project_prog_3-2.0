package com.musicspring.app.music_app.model.dto;

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
