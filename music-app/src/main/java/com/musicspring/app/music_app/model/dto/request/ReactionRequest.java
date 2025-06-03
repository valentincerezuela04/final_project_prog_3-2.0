package com.musicspring.app.music_app.model.dto.request;

import com.musicspring.app.music_app.model.enums.CommentType;
import com.musicspring.app.music_app.model.enums.ReactedType;
import com.musicspring.app.music_app.model.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Request object for creating a reaction")
public class ReactionRequest {

    @Schema(description = "ID of the user making the reaction", example = "1")
    @NotNull(message = "User ID is required")
    private Long userId;

    @Schema(description = "Type of the reaction (Like, dislike, love)", example = "LOVE")
    @NotNull(message = "Reaction type is required")
    private ReactionType reactionType;

    @Schema(description = "Type of the object reacted (Comment or review)", example = "COMMENT")
    @NotNull(message = "Reacted type is required")
    private ReactedType reactedType;

    @Schema(description = "ID of the object being reacted to (Review or Comment)", example = "10")
    @NotNull(message = "Reacted ID is required")
    private Long reactedId;

    @Schema(description = "Type of the review if reactedType is REVIEW (SONG_REVIEW or ALBUM_REVIEW)", example = "SONG_REVIEW")
    private CommentType commentType;

}
