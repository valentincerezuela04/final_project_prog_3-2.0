package com.musicspring.app.music_app.review.albumReview.repository;

import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity,Long> {
}
