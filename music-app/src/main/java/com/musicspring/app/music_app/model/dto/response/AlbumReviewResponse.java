package com.musicspring.app.music_app.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response payload for an album review")

public class AlbumReviewResponse {

    @Schema(description = "Unique identifier of the album review", example = "1")
    private Long albumReviewId;

    @Schema(description = "Rating given to the album (between 0.5 and 5.0)", example = "4.5")
    private Double rating;

    @Schema(description = "Textual description of the review", example = "Great album with amazing vocals!")
    private String description;

    @Schema(description = "Date and time when the review was created")
    private LocalDateTime date;

    @Schema(description = "Whether the review is active", example = "true")
    private Boolean active;

    @Schema(description = "User who wrote the review")
    private UserResponse user;

    @Schema(description = "Album being reviewed")
    private AlbumResponse album;


}