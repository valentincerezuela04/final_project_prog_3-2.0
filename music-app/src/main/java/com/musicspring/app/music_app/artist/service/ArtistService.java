package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.shared.IService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ArtistService implements IService<ArtistEntity> {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public void deleteById(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new EntityNotFoundException("Artist with id " + id + " not found");
        }
        artistRepository.deleteById(id);
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
        return artistRepository.save(artistEntity);
    }
}
