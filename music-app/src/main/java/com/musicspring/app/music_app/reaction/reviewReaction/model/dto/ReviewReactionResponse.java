package com.musicspring.app.music_app.reaction.reviewReaction.model.dto;

import com.musicspring.app.music_app.reaction.common.model.dto.ReactionResponse;
import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReviewReactionResponse extends ReactionResponse {

    // Asumiendo que esta va a ser la response que queremos mostrar aca
    private UserResponse user;
    private Long reviewId;

    /// Static factory method to create a ReviewReactionResponse from an entity using the UserMapper.
    public static ReviewReactionResponse of(@NotNull ReviewReactionEntity entity, UserMapper userMapper) {
        ReviewReactionResponse response = new ReviewReactionResponse();
        response.setReactionId(entity.getReactionId());
        response.setReactionType(entity.getReactionType());
        response.setUserId(entity.getUser().getUserId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUser(userMapper.toResponse(entity.getUser()));
        response.setReviewId(entity.getAssociatedReviewId());
        return response;
    }
}
