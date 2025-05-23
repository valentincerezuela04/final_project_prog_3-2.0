package com.musicspring.app.music_app.artist.repository;

import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistXSongRepository extends JpaRepository<ArtistXSongEntity, ArtistXSongId> {
    List<ArtistXSongEntity> findByArtistArtistId(Long artistId);

}