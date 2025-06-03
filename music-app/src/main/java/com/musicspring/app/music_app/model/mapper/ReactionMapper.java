package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.ReactionRequest;
import com.musicspring.app.music_app.model.dto.ReactionResponse;
import com.musicspring.app.music_app.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReactionMapper {

    public ReactionResponse toResponse(ReactionEntity reaction){
        if(reaction == null) return null;

        return ReactionResponse.builder()
                .reactionId(reaction.getId())
                .userId(reaction.getUser().getUserId())
                .reactionType(reaction.getReactionType())
                .reactedType(reaction.getReactedType())
                .reactedId(reaction.getReview() != null ? reaction.getReview().getReviewId() : reaction.getComment().getCommentId())
                .build();
    }

    public Page<ReactionResponse> toResponsePage(Page<ReactionEntity> reactionPage){
        if(reactionPage == null) return null;

        return reactionPage.map(this::toResponse);
    }

    public List<ReactionResponse> toResponseList(List<ReactionEntity> reactionList) {
        if (reactionList == null) return null;

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
                .createdAt(LocalDateTime.now())
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
                .createdAt(LocalDateTime.now())
                .user(userEntity)
                .review(reviewEntity)
                .build();
    }


}
