package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.artist.model.mapper.ArtistMapper;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import com.musicspring.app.music_app.shared.IService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService implements IService<ArtistEntity> {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final ArtistXSongRepository artistXSongRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ArtistMapper artistMapper, ArtistXSongRepository artistXSongRepository) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.artistXSongRepository = artistXSongRepository;
    }

    public ArtistWithSongsResponse getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findById(artistId);
        List<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId);
        return artistMapper.toArtistWithSongsResponse(artist, relations);
    }

    @Override
    public void deleteById(Long id) {
        ArtistEntity artist = findById(id);
        artist.setActive(false);
        artistRepository.save(artist);
    }

    @Override
    public Page<ArtistEntity> findAll(Pageable pageable) {
        return artistRepository.findByActiveTrue(pageable);
    }

    @Override
    public ArtistEntity findById(Long id) {
        return artistRepository.findByArtistIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist with id " + id + " not found"));
    }

    @Override
    public ArtistEntity save(ArtistEntity artistEntity) {
        if(artistEntity == null) {
            throw new IllegalArgumentException("ArtistEntity cannot be null");
        }
        artistEntity.setActive(true);
        return artistRepository.save(artistEntity);
    }

    public List<ArtistEntity> findByName(String name) {
        return artistRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
    }

    public ArtistEntity update(Long id, ArtistEntity artistDetails) {
        ArtistEntity existingArtist = findById(id);
        existingArtist.setName(artistDetails.getName());
        if (artistDetails.getFollowers() != null) {
            existingArtist.setFollowers(artistDetails.getFollowers());
        }
        return artistRepository.save(existingArtist);
    }
}