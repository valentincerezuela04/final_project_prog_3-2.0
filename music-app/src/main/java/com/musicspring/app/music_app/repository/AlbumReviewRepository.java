package com.musicspring.app.music_app.repository;

import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.model.entity.SongReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumReviewRepository extends JpaRepository<AlbumReviewEntity,Long> {

    @Query("SELECT ar FROM AlbumReviewEntity ar WHERE ar.album.albumId = :albumId")
    Page<AlbumReviewEntity> findByAlbum_AlbumId(Long albumId, Pageable pageable);

    @Query("SELECT ar FROM AlbumReviewEntity ar WHERE ar.user.userId = :userId")
    Page<AlbumReviewEntity> findByUser_UserId(Long userId,Pageable pageable);

    @Query("SELECT ar FROM AlbumReviewEntity ar WHERE ar.album.spotifyId = :spotifyId")
    Page<AlbumReviewEntity> findByAlbum_SpotifyId(@Param("spotifyId") String spotifyId, Pageable pageable);

    long countByUser_UserId(Long userId);


}
