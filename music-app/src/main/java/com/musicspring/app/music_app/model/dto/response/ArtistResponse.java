package com.musicspring.app.music_app.model.dto.response;

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
    private String imageUrl;
}
