package com.musicspring.app.music_app.artist.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistXSongRequest {
    @NotNull
    private Long artistId;

    @NotNull
    private Long songId;
}