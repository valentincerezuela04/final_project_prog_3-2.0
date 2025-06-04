package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.request.ReactionRequest;
import com.musicspring.app.music_app.model.dto.response.ReactionResponse;
import com.musicspring.app.music_app.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReactionMapper {

    public ReactionResponse toResponse(ReactionEntity reaction){
        return ReactionResponse.builder()
                .reactionId(reaction.getId())
                .userId(reaction.getUser().getUserId())
                .username(reaction.getUser().getUsername())
                .reactionType(reaction.getReactionType())
                .reactedType(reaction.getReactedType())
                .reactedId(reaction.getReview() != null
                        ? reaction.getReview().getReviewId()
                        : reaction.getComment().getCommentId())
                .build();
    }

    public Page<ReactionResponse> toResponsePage(Page<ReactionEntity> reactionPage){
        return reactionPage.map(this::toResponse);
    }

    public List<ReactionResponse> toResponseList(List<ReactionEntity> reactionList) {
        return reactionList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReactionEntity toEntity(ReactionRequest reactionRequest,
                                   UserEntity userEntity,
                                   CommentEntity commentEntity){
        return ReactionEntity.builder()
                .reactedType(reactionRequest.getReactedType())
                .reactionType(reactionRequest.getReactionType())
                .user(userEntity)
                .comment(commentEntity)
                .build();
    }

    public ReactionEntity toEntity(ReactionRequest reactionRequest,
                                   UserEntity userEntity,
                                   ReviewEntity reviewEntity){
        return ReactionEntity.builder()
                .reactedType(reactionRequest.getReactedType())
                .reactionType(reactionRequest.getReactionType())
                .user(userEntity)
                .review(reviewEntity)
                .build();
    }
}

