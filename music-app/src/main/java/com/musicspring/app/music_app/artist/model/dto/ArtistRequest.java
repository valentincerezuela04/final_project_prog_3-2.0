package com.musicspring.app.music_app.artist.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ArtistRequest", description = "Request DTO to create or update an artist")
public class ArtistRequest {

    @NotBlank
    @Schema(description = "Name of the artist", example = "The Beatles", required = true)
    private String name;

    @NotNull
    @Min(value = 0, message = "Followers must be non-negative")
    @Schema(description = "Number of followers the artist has (non-negative integer)", example = "5000000", required = true)
    private Integer followers;

    @Schema(description = "Flag indicating if the artist is currently active", example = "true")
    private boolean active;
}
