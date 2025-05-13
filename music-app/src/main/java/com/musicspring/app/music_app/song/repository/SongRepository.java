package com.musicspring.app.music_app.song.repository;

import com.musicspring.app.music_app.song.model.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findBySpotifyId(String spotifyId);

    void softDelete(Long id);
}
