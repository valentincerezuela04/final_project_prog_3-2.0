package com.musicspring.app.music_app.song.service;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SongService  {

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public SongService(SongRepository songRepository, SongMapper songMapper) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    public Page<SongResponse> findAll(Pageable pageable) {
        return songMapper.toResponsePage(songRepository.findAll(pageable));
    }

    public SongResponse findById(Long id) {
        return songMapper.toResponse(songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with ID: " + id + " was not found.")));
    }

    public void deleteById(Long id) {
        SongEntity songEntity = songRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Song with ID: " + id + " was not found."));
        songEntity.setActive(false);
        songRepository.save(songEntity);
    }

    public SongResponse saveSong(SongEntity songEntity) {
        songEntity.setActive(true);
        return songMapper.toResponse(songRepository.save(songEntity));
    }

    public SongEntity songResponseToEntity(SongResponse songResponse){
        return songMapper.toEntity(songResponse);
    }

    public SongResponse findBySpotifyId(String spotifyId) {
        return songMapper.toResponse(songRepository.findBySpotifyId(spotifyId)
                .orElseThrow(() -> new EntityNotFoundException("Song with Spotify ID: " + spotifyId + " was not found.")));
    }

    public boolean existsById (Long id){
        return songRepository.existsById(id);
    }

    public Page<SongResponse> searchSongs(String query, Pageable pageable) {
        Page<SongEntity> songPage = songRepository.findByNameContainingIgnoreCaseOrArtistNameContainingIgnoreCase(
                query, query, pageable
        );

        return songMapper.toResponsePage(songPage);
    }
}
