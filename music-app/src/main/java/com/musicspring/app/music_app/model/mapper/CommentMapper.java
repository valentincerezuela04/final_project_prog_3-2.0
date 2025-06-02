package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.CommentRequest;
import com.musicspring.app.music_app.model.dto.CommentResponse;
import com.musicspring.app.music_app.model.entity.CommentEntity;
import com.musicspring.app.music_app.model.entity.ReviewEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponse toResponse(CommentEntity comment){
        if(comment == null) return null;

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .reviewId(comment.getReviewEntity().getReviewId())
                .userId(comment.getUser().getUserId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .commentType(comment.getCommentType())
                .username(comment.getUser().getUsername())
                .build();
    }

    public Page<CommentResponse> toResponsePage(Page<CommentEntity> commentPage){
        if(commentPage == null) return null;

        return commentPage.map(this::toResponse);
    }

    public CommentEntity toEntity(CommentRequest commentRequest,
                                  UserEntity userEntity,
                                  ReviewEntity reviewEntity){

        return CommentEntity.builder()
                .text(commentRequest.getText())
                .user(userEntity)
                .reviewEntity(reviewEntity)
                .commentType(commentRequest.getCommentType())
                .build();
    }

}
