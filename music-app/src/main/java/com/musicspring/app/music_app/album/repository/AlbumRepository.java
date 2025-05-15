package com.musicspring.app.music_app.album.repository;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findBySpotifyId(String spotifyId);
    Page<AlbumEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<AlbumEntity> findAllByArtistId(Long id); /// revisar al momento de tener ArtistEntity
}
