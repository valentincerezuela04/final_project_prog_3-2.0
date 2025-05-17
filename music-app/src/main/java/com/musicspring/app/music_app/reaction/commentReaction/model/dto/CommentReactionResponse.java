package com.musicspring.app.music_app.reaction.commentReaction.model.dto;

import com.musicspring.app.music_app.reaction.common.model.dto.ReactionResponse;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

//Esta clase sera usada cuando se tenga la clase CommentEntity creada
public class CommentReactionResponse extends ReactionResponse {

    private UserResponse user;
    private Long commentId;

    /// Static factory method to create a CommentReactionResponse from an entity using the UserMapper.
//    public static CommentReactionResponse of(CommentReactionEntity entity, UserMapper userMapper) {
//        CommentReactionResponse response = new CommentReactionResponse();
//        response.setReactionId(entity.getReactionId());
//        response.setType(entity.getType());
//        response.setUserId(entity.getUser().getUserId());
//        response.setCreatedAt(entity.getCreatedAt());
//        response.setUser(userMapper.toResponse(entity.getUser()));
//        response.setCommentId(entity.getComment().getCommentId());
//        return response;
//    }

}
