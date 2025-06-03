package com.musicspring.app.music_app.model.dto;

import com.musicspring.app.music_app.model.enums.ReactedType;
import com.musicspring.app.music_app.model.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "Response object with reaction details")
public class ReactionResponse {

    @Schema(description = "Unique identifier of the reaction", example = "3")
    private Long reactionId;

    @Schema(description = "ID of the user who made the reaction", example = "1")
    private Long userId;

    @Schema(description = "Type of the reaction (Like, dislike, love)", example = "LOVE")
    private ReactionType reactionType;

    @Schema(description = "Type of the object reacted (Comment or review)", example = "COMMENT")
    private ReactedType reactedType;

    @Schema(description = "ID of the object reacted", example = "5")
    private Long reactedId;

    @Schema(description = "Username of the user who made the reaction", example = "music_lover_123")
    private String username;
}
