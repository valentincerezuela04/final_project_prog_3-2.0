package com.musicspring.app.music_app.song.service;

import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("Song with ID: " + id + " was not found.")); ///Reemplazar por una excepcion personalizada
    }

    public Optional<SongEntity> findBySpotifyId(String spotifyId) {
        return songRepository.findBySpotifyId(spotifyId);
    }

    @Override
    public void deleteById(Long id) {
        SongEntity songEntity = findById(id);

        if (!songEntity.getActive()) {
            System.out.println("This song has already been deleted."); ///Reemplazar por una excepcion personalizada
            return;
        }

        songEntity.setActive(false);
        songRepository.save(songEntity);
    }

    @Override
    public void save(SongEntity songEntity) {
        songRepository.save(songEntity);
    }

    public List<SongEntity> search(String query) {
        return songRepository.findByNameContainingIgnoreCaseOrArtistNameContainingIgnoreCase(query, query);
    }
}
