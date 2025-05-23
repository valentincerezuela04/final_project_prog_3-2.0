package com.musicspring.app.music_app.artist.repository;

import com.musicspring.app.music_app.artist.model.dto.ArtistResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {

    Optional<ArtistEntity> findByArtistIdAndActiveTrue(Long artistId);

    Page<ArtistEntity> findByActiveTrue(Pageable pageable);

    List<ArtistResponse> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    boolean existsByArtistIdAndActiveTrue(Long artistId);
}