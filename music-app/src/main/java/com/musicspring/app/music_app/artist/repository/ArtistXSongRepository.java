package com.musicspring.app.music_app.artist.repository;

import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistXSongRepository extends JpaRepository<ArtistXSongEntity,Long> {
    List<ArtistXSongEntity> findByArtistArtistId(Long artistId);

}
