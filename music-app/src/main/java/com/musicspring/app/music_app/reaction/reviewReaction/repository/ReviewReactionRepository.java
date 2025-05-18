package com.musicspring.app.music_app.reaction.reviewReaction.repository;

import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReactionEntity, Long> {

    /// Finds all reactions linked to a specific album review by its ID
    Page<ReviewReactionEntity> findByAlbumReview_AlbumReviewId(Long albumReviewId, Pageable pageable);
    /// Finds all reactions linked to a specific song review by its ID
    Page<ReviewReactionEntity> findBySongReview_SongReviewId(Long songReviewId, Pageable pageable);
    /// Finds all reactions created by a specific user by user ID
    Page<ReviewReactionEntity> findByUser_UserId(Long userId, Pageable pageable);
}
