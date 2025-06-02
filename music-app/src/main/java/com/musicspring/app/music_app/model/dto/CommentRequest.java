package com.musicspring.app.music_app.model.dto;


import com.musicspring.app.music_app.model.enums.CommentType;
import com.musicspring.app.music_app.model.enums.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Request object for creating a comment")
public class CommentRequest {

    @Schema(description = "ID of the user making the comment", example = "1")
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(description = "Text content of the comment", example = "Great reviewEntity, thanks for sharing!")
    @NotBlank(message = "Comment text cannot be blank")
    private String text;

    @Schema(description = "Type of the comment (song or album reviewEntity)", example = "SONG_REVIEW")
    @NotNull(message = "Comment type is required")
    private CommentType commentType;

    @Schema(description = "Indicates whether the review is for a song or an album", example = "SONG")
    @NotNull(message = "Review type is required")
    private ReviewType reviewType;

}
