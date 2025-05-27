package com.musicspring.app.music_app.review.songReview.model.dto;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response payload for a song reviewEntity")
public class SongReviewResponse {
    
    @Schema(description = "Unique identifier of the song reviewEntity", example = "1")
    private Long songReviewId;

    @Schema(description = "Rating given to the song (between 0.5 and 5.0)", example = "4.5")
    private Double rating;

    @Schema(description = "Textual description of the reviewEntity", example = "Great song with amazing vocals!")
    private String description;

    @Schema(description = "Date and time when the reviewEntity was created")
    private LocalDateTime date;

    @Schema(description = "Whether the reviewEntity is active", example = "true")
    private Boolean active;

    @Schema(description = "User who wrote the reviewEntity")
    private UserResponse user;

    @Schema(description = "Song being reviewed")
    private SongResponse song;
}