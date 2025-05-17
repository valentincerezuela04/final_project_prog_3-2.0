package com.musicspring.app.music_app.reaction.reviewReaction.repository;

import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReactionEntity, Long> {

    /// Finds all reactions linked to a specific album review by its ID
    List<ReviewReactionEntity> findByAlbumReview_AlbumReviewId(Long albumReviewId);
    /// Finds all reactions linked to a specific song review by its ID
    List<ReviewReactionEntity> findBySongReview_SongReviewId(Long songReviewId);
    /// Finds all reactions created by a specific user by user ID
    List<ReviewReactionEntity> findByUser_UserId(Long userId);
}
