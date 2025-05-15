package com.musicspring.app.music_app.song.service;

import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService implements IService<SongEntity> {

    private final SongRepository songRepository;
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Page<SongEntity> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public SongEntity findById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with ID: " + id + " was not found."));
    }

    @Override
    public void deleteById(Long id) {
        SongEntity songEntity = findById(id);
        songEntity.setActive(false);
        songRepository.save(songEntity);
    }

    @Override
    public SongEntity save(SongEntity songEntity) {
        songEntity.setActive(true);
        return songRepository.save(songEntity);
    }

    public SongEntity findBySpotifyId(String spotifyId) {
        return songRepository.findBySpotifyId(spotifyId)
                .orElseThrow(() -> new EntityNotFoundException("Song with Spotify ID: " + spotifyId + " was not found."));
    }

    public boolean existsById (Long id){
        return songRepository.existsById(id);
    }

    public List<SongEntity> search(String query) {
        return songRepository.findByNameContainingIgnoreCaseOrArtistNameContainingIgnoreCase(query, query);
    }
}
