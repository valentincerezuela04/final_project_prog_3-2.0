package com.musicspring.app.music_app.reaction.common.model.dto;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public abstract class ReactionResponse {
    private Long reactionId;
    private ReactionType reactionType;
    private Long userId;
    private LocalDateTime createdAt;
}
