package com.musicspring.app.music_app.reaction.common.model.dto;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/// Shows who reacted and the type of reaction
public class ReactionDetailResponse {

    private ReactionType reactionType;
    private Long userId;
    private String username;
}
