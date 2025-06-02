package com.musicspring.app.music_app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for artist operations")
public class ArtistRequest {

    @Schema(description = "Spotify ID of the artist", example = "0OdUWJ0sBjDrqHygGUXeCF")
    private String spotifyId;

    @Schema(description = "Name of the artist", example = "Taylor Swift")
    private String name;

    @Schema(description = "Number of followers the artist has on Spotify", example = "84573485")
    private Integer followers;

    @Schema(description = "URL of the artist's image", example = "https://i.scdn.co/image/ab6761610000e5eb1b5c9ad7e921fa9a9fbb2d0e")
    private String imageUrl;

    @Schema(description = "Maximum number of results to return", example = "10")
    private Integer limit;

    @Schema(description = "Result page offset", example = "0")
    private Integer offset;

    @Schema(description = "Sort results by specific field", example = "popularity")
    private String sortBy;
}
