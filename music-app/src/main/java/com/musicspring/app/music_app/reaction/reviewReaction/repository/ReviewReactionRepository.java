package com.musicspring.app.music_app.reaction.reviewReaction.repository;

import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReactionEntity, Long> {

    /// Finds all reactions linked to a specific album reviewEntity by its ID
    Page<ReviewReactionEntity> findByAlbumReview_ReviewId(Long albumReviewId, Pageable pageable);
    /// Finds all reactions linked to a specific song reviewEntity by its ID
    Page<ReviewReactionEntity> findBySongReview_ReviewId(Long songReviewId, Pageable pageable);
    /// Finds all reactions created by a specific user by user ID
    Page<ReviewReactionEntity> findByUser_UserId(Long userId, Pageable pageable);
    /// Finds all reactions linked to reviews of a specific album by album ID
    Page<ReviewReactionEntity> findByAlbumReview_Album_AlbumId(Long albumId, Pageable pageable);
}
