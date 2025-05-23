package com.musicspring.app.music_app.review.songReview.repository;

import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SongReviewRepository extends JpaRepository<SongReviewEntity,Long> {
    @Query("SELECT sr FROM SongReviewEntity sr WHERE sr.song.songId = :songId")
    Page<SongReviewEntity> findBySong_Id(@Param("songId") Long songId, Pageable pageable);

    @Query("SELECT sr FROM SongReviewEntity sr WHERE sr.user.userId = :userId")
    Page<SongReviewEntity> findByUser_UserId(@Param("userId") Long userId, Pageable pageable);
    
    @Query("SELECT sr FROM SongReviewEntity sr WHERE sr.song.spotifyId = :spotifyId")
    Page<SongReviewEntity> findBySong_SpotifyId(@Param("spotifyId") String spotifyId, Pageable pageable);
}