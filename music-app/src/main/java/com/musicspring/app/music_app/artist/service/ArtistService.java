package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongId;
import com.musicspring.app.music_app.artist.model.mapper.ArtistMapper;
import com.musicspring.app.music_app.artist.model.mapper.ArtistXSongMapper;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.service.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService implements IService<ArtistEntity> {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final ArtistXSongRepository artistXSongRepository;
    private final ArtistXSongMapper artistXSongMapper;
    private final SongService songService;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper,
                         ArtistXSongRepository artistXSongRepository,
                         ArtistXSongMapper artistXSongMapper,
                         SongService songService) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.artistXSongRepository = artistXSongRepository;
        this.artistXSongMapper = artistXSongMapper;
        this.songService = songService;
    }

    public ArtistWithSongsResponse getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findById(artistId);
        List<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId);
        return artistMapper.toArtistWithSongsResponse(artist, relations);
    }

    @Override
    @Transactional
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

    public boolean existsById(Long id) {
        return artistRepository.existsByArtistIdAndActiveTrue(id);
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

    @Transactional
    public ArtistXSongResponse createArtistSongRelationship(Long artistId, Long songId) {
        ArtistEntity artist = findById(artistId);
        SongResponse song = songService.findById(songId);
        SongEntity songEntity = songService.songResponseToEntity(song);

        ArtistXSongId id = new ArtistXSongId(artistId, songId);

        // Check if already exists
        if (artistXSongRepository.existsById(id)) {
            throw new IllegalStateException("Relationship between artist " + artistId + " and song " + songId + " already exists");
        }

        ArtistXSongEntity relation = ArtistXSongEntity.builder()
                .artistXSongId(id)
                .artist(artist)
                .song(songEntity)
                .build();

        ArtistXSongEntity savedRelation = artistXSongRepository.save(relation);
        return artistXSongMapper.toResponse(savedRelation);
    }

    @Transactional
    public void deleteArtistSongRelationship(Long artistId, Long songId) {
        ArtistXSongId id = new ArtistXSongId(artistId, songId);
        if (!artistXSongRepository.existsById(id)) {
            throw new EntityNotFoundException("Relationship between artist " + artistId + " and song " + songId + " not found");
        }
        artistXSongRepository.deleteById(id);
    }

    public List<ArtistXSongResponse> getSongsByArtistId(Long artistId) {
        if (!existsById(artistId)) {
            throw new EntityNotFoundException("Artist with id " + artistId + " not found");
        }

        List<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId);
        return artistXSongMapper.toResponseList(relations);
    }

    public List<ArtistXSongResponse> getArtistsBySongId(Long songId) {
        if (!songService.existsById(songId)) {
            throw new EntityNotFoundException("Song with id " + songId + " not found");
        }

        List<ArtistXSongEntity> relations = artistXSongRepository.findBySongSongId(songId);
        return artistXSongMapper.toResponseList(relations);
    }
}