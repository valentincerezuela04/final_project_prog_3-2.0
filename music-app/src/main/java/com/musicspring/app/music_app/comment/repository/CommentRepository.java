package com.musicspring.app.music_app.comment.repository;

import com.musicspring.app.music_app.comment.model.entity.CommentEntity;
import com.musicspring.app.music_app.comment.model.entity.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

    Page<CommentEntity> findByReviewEntity_ReviewId(Long reviewId, Pageable pageable);

    Page<CommentEntity> findByUser_UserId(Long userId, Pageable pageable);

    Page<CommentEntity> findByReviewEntity_ReviewIdAndCommentType(Long reviewId, CommentType commentType, Pageable pageable);
}
