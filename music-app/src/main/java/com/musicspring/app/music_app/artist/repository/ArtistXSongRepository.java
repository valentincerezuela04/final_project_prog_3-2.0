package com.musicspring.app.music_app.artist.repository;

import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistXSongRepository extends JpaRepository<ArtistXSongEntity,Long> {
}
