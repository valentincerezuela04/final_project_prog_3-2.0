package com.musicspring.app.music_app.artist.repository;


import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {
}
