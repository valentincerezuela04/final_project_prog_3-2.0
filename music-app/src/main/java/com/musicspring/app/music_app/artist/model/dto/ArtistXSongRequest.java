package com.musicspring.app.music_app.artist.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ArtistXSongRequest", description = "Request DTO to create or delete the relationship between an artist and a song")
public class ArtistXSongRequest {

    @NotNull
    @Schema(description = "Unique identifier of the artist", example = "1", required = true)
    private Long artistId;

    @NotNull
    @Schema(description = "Unique identifier of the song", example = "10", required = true)
    private Long songId;
}
