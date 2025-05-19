package com.musicspring.app.music_app.album.repository;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findBySpotifyId(String spotifyId);

    List<AlbumEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT a FROM AlbumEntity a WHERE a.artist.id = :artistId") // jpql
    List<AlbumEntity> findAllByArtistId(@Param("artistId") Long artistId);
}
