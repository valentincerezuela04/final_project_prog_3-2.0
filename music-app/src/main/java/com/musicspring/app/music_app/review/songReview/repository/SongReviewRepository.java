package com.musicspring.app.music_app.review.songReview.repository;

import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongReviewRepository extends JpaRepository<SongReviewEntity,Long> {
}
