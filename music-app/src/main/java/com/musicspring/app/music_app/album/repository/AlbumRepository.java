package com.musicspring.app.music_app.album.repository;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    public List<AlbumEntity> findByTitleIgnoreCase(String title);
    public Optional<AlbumEntity> findBySpotifyId(String spotifyId);
    public void softDelete (Long id);
}
