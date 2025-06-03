package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.ArtistRequest;
import com.musicspring.app.music_app.model.dto.ArtistResponse;
import com.musicspring.app.music_app.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.model.entity.ArtistEntity;
import com.musicspring.app.music_app.model.mapper.ArtistMapper;
import com.musicspring.app.music_app.repository.ArtistRepository;
import com.musicspring.app.music_app.model.mapper.SongMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    public ArtistWithSongsResponse getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findById(artistId);
        return artistMapper.toArtistWithSongsResponse(artist);
    }

    @Transactional
    public void deleteById(Long id) {
        ArtistEntity artist = findById(id);

        if (!artist.isActive()) {
            throw new IllegalStateException("Artist with id " + id + " is already inactive");
        }

        artist.setActive(false);
        artistRepository.save(artist);
    }


    public Page<ArtistEntity> findAll(Pageable pageable) {
        return artistRepository.findByActiveTrue(pageable);
    }

    public ArtistEntity findById(Long id) {
        return artistRepository.findByArtistIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist with id " + id + " not found"));
    }

    public ArtistResponse save(ArtistRequest artistRequest) {
        if (artistRequest == null) {
            throw new IllegalArgumentException("Artist request must not be null");
        }
        ArtistEntity entity = artistMapper.toEntity(artistRequest);
        entity.setActive(true);
        ArtistEntity saved = artistRepository.save(entity);
        return artistMapper.toResponse(saved);
    }

    public List<ArtistResponse> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search parameter 'name' must not be null or empty");
        }
        return artistRepository.findByNameContainingIgnoreCaseAndActiveTrue(name.trim());
    }

    public ArtistResponse getArtistResponseById(Long id) {
        return artistRepository.findByArtistIdAndActiveTrue(id)
                .map(artistMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Artist with id " + id + " not found"));

    }
    public Page<ArtistResponse> getAllArtists(Pageable pageable) {
        return artistRepository.findByActiveTrue(pageable)
                .map(artistMapper::toResponse);
    }
}