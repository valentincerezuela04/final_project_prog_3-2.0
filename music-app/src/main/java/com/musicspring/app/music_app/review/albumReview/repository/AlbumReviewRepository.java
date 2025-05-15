package com.musicspring.app.music_app.review.albumReview.repository;

import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity,Long> {
    Page<AlbumReviewEntity> findByAlbum_AlbumId(Long albumId, Pageable pageable);
    Page<AlbumReviewEntity> findByUser_UserId(Long userId,Pageable pageable);
}
