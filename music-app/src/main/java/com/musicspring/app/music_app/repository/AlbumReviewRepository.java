package com.musicspring.app.music_app.repository;

import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity,Long> {
    Page<AlbumReviewEntity> findByAlbum_AlbumId(Long albumId, Pageable pageable);
    Page<AlbumReviewEntity> findByUser_UserId(Long userId,Pageable pageable);
    long countByUser_UserId(Long userId);


}
