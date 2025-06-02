package com.musicspring.app.music_app.model.dto;

import com.musicspring.app.music_app.model.enums.CommentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Response object with comment details")
public class CommentResponse {

    @Schema(description = "Unique identifier of the comment", example = "3")
    private Long commentId;

    @Schema(description = "ID of the reviewEntity commented on", example = "1")
    private Long reviewId;

    @Schema(description = "ID of the user who made the comment", example = "10")
    private Long userId;

    @Schema(description = "Text content of the comment", example = "Great reviewEntity, thanks for sharing!")
    private String text;

    @Schema(description = "Timestamp when the comment was created", example = "2025-05-27T18:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Type of the comment (song or album reviewEntity)", example = "SONG_REVIEW")
    private CommentType commentType;

    @Schema(description = "Username of the user who made the comment", example = "music_lover_123")
    private String username;

}
