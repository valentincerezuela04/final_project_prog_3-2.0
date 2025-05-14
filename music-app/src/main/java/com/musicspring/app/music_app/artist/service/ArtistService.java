package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsDto;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.mapping.ArtistMapping;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import com.musicspring.app.music_app.shared.IService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ArtistService implements IService<ArtistEntity> {
    private final ArtistRepository artistRepository;
    private final ArtistMapping artistMapping;
    private final ArtistXSongRepository artistXSongRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,ArtistMapping artistMapping,ArtistXSongRepository artistXSongRepository) {
        this.artistRepository = artistRepository;
        this.artistMapping = artistMapping;
        this.artistXSongRepository = artistXSongRepository;

    }

    public ArtistWithSongsDto getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findById(artistId);
        List<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId);
        return artistMapping.toDTO(artist, relations);
    }

    @Override
    public void deleteById(Long id) {
        ArtistEntity artist = findById(id);
        artist.setActive(false);
        artistRepository.save(artist);
    }

    @Override
    public Page<ArtistEntity> findAll(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    @Override
    public ArtistEntity findById(Long id) {
        return artistRepository.findById(id)
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
}
