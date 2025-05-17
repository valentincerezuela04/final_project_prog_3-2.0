package com.musicspring.app.music_app.reaction.common.model.mapper;

import com.musicspring.app.music_app.reaction.common.model.dto.ReactionDetailResponse;
import com.musicspring.app.music_app.reaction.common.model.dto.ReactionSummaryResponse;
import com.musicspring.app.music_app.reaction.reviewReaction.model.dto.ReviewReactionResponse;
import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReactionMapper {

    private final UserMapper userMapper;

    public ReactionMapper(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public ReviewReactionResponse toReviewReactionResponse(ReviewReactionEntity entity){
        if (entity == null) return null;
        return  ReviewReactionResponse.of(entity, userMapper);
    }

//    Metodo para implementar cuando se tenga la funcionalidad de comentarios creada
//    public CommentReactionResponse toCommentReactionResponse(CommentReactionEntity entity) {
//        if (entity == null) return null;
//        return CommentReactionResponse.of(entity, userMapper);
//    }

    public ReactionSummaryResponse toReactionSummaryResponse(ReactionType type, Long count){
        return ReactionSummaryResponse.builder()
                .type(type)
                .count(count != null ? count : 0L)
                .build();
    }

    public ReactionDetailResponse toReactionDetailResponse(UserEntity user, ReactionType type){
        return ReactionDetailResponse.builder()
                .username(user.getUsername())
                .reactionType(type)
                .build();
    }

    public List<ReviewReactionResponse> toReviewReactionResponseList(List<ReviewReactionEntity> entities){
        return entities.stream()
                .map(this::toReviewReactionResponse)
                .toList();
    }

//   Metodo para implementar cuando se tenga la funcionalidad de comentarios creada
//    public List<CommentReactionResponse> toCommentReactionResponseList(List<CommentReactionEntity> entities){
//        return entities.stream()
//                .map(this::toCommentReactionResponse)
//                .toList();
//    }

    public Page<ReviewReactionResponse> toReviewReactionResponsePage(Page<ReviewReactionEntity> entityPage){
        if(entityPage == null){
            return Page.empty();
        }
        return entityPage.map(this::toReviewReactionResponse);
    }

//    Metodo para implementar cuando se tenga la funcionalidad de comentarios creada
//    public Page<CommentReactionResponse> toCommentReactionResponsePage(Page<CommentReactionEntity> entityPage){
//        if(entityPage == null){
//            return Page.empty();
//        }
//        return entityPage.map(this::toCommentReactionResponse);
//    }

}
