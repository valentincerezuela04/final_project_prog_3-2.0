package com.musicspring.app.music_app.album.repository;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findBySpotifyId(String spotifyId);
    List<AlbumEntity> findByTitleContainingIgnoreCase(String title);
    List<AlbumEntity> findAllByArtistId(Long id); /// revisar al momento de tener ArtistEntity
}
