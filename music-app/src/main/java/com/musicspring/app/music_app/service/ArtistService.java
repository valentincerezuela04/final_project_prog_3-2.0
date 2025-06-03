package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.request.ArtistRequest;
import com.musicspring.app.music_app.model.dto.response.ArtistResponse;
import com.musicspring.app.music_app.model.dto.response.ArtistWithSongsResponse;
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
public class ArtistService{
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final SongService songService;
    private final SongMapper songMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper,
                         SongService songService,
                         SongMapper songMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.songService = songService;
        this.songMapper = songMapper;
    }

    public ArtistWithSongsResponse getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findById(artistId);
        return artistMapper.toArtistWithSongsResponse(artist);
    }


    @Transactional
    public void deleteById(Long id) {
        ArtistEntity artist = findById(id);
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

    public boolean existsById(Long id) {
        return artistRepository.existsByArtistIdAndActiveTrue(id);
    }


    public ArtistResponse save(ArtistRequest artistRequest) {
        ArtistEntity entity = artistMapper.toEntity(artistRequest);
        entity.setActive(true);
        ArtistEntity saved = artistRepository.save(entity);
        return artistMapper.toResponse(saved);
    }


    public List<ArtistResponse> findByName(String name) {
        return artistRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }




    public ArtistResponse getArtistResponseById(Long id) {
        ArtistEntity entity = findById(id);
        return artistMapper.toResponse(entity);
    }

    public Page<ArtistResponse> getAllArtists(Pageable pageable) {
        return artistRepository.findByActiveTrue(pageable)
                .map(artistMapper::toResponse);
    }
}