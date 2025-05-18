package com.musicspring.app.music_app.reaction.commentReaction.model.dto;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CommentReactionRequest {

    @NotNull(message = "Reaction type must not be null")
    private ReactionType reactionType;


    /// Possible properties for CommentReactionEntity
//    public CommentReactionEntity toEntity(@NotNull UserEntity user, @NotNull CommentEntity comment){
//        return CommentReactionEntity.builder()
//                .reactionType(reactionType)
//                .user(user)
//                .comment(comment)
//                .build();
//
//    }
}
