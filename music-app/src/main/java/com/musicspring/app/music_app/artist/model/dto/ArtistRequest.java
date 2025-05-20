package com.musicspring.app.music_app.artist.model.dto;

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
public class ArtistRequest {
    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0, message = "Followers must be non-negative")
    private Integer followers;

    private boolean active;
}