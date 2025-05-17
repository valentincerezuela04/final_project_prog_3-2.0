package com.musicspring.app.music_app.reaction.common.model.dto;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

/// Represents a summary of reactions grouped by type
public class ReactionSummaryResponse {

    private ReactionType type;
    private Long count;
}
