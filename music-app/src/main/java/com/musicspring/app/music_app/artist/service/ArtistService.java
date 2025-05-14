package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ArtistService {
    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<ArtistEntity> getAll(){
        return artistRepository.findAll();
    }

    public Optional<ArtistEntity> getbyId(Long id){
        return artistRepository.findById(id);
    }

    public ArtistEntity save(ArtistEntity artist){
        return artistRepository.save(artist);

    }

    public void delete(Long id){
        artistRepository.deleteById(id);
    }
}
