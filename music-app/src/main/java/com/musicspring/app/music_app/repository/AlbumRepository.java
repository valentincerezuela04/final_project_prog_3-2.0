package com.musicspring.app.music_app.repository;

import com.musicspring.app.music_app.model.entity.AlbumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findBySpotifyId(String spotifyId);

    List<AlbumEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
