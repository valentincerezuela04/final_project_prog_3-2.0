package com.musicspring.app.music_app.artist.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {
    private Long artistId;
    private String name;
    private Integer followers;
}
