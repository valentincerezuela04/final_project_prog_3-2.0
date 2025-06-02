package com.musicspring.app.music_app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response payload for a song review")
public class SongReviewResponse {
    
    @Schema(description = "Unique identifier of the song review", example = "1")
    private Long songReviewId;

    @Schema(description = "Rating given to the song (between 0.5 and 5.0)", example = "4.5")
    private Double rating;

    @Schema(description = "Textual description of the review", example = "Great song with amazing vocals!")
    private String description;

    @Schema(description = "Date and time when the review was created")
    private LocalDateTime date;

    @Schema(description = "Whether the review is active", example = "true")
    private Boolean active;

    @Schema(description = "User who wrote the review")
    private UserResponse user;

    @Schema(description = "Song being reviewed")
    private SongResponse song;
}