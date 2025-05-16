package com.musicspring.app.music_app.artist.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistXSongResponse {
    private Long artistId;
    private Long songId;
}

