package com.musicspring.app.music_app.repository;

import com.musicspring.app.music_app.model.entity.CommentEntity;
import com.musicspring.app.music_app.model.enums.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

    Page<CommentEntity> findByReviewEntity_id(Long reviewId, Pageable pageable);

    Page<CommentEntity> findByUser_UserId(Long userId, Pageable pageable);

    Page<CommentEntity> findByReviewEntity_idAndCommentType(Long reviewId, CommentType commentType, Pageable pageable);
}
