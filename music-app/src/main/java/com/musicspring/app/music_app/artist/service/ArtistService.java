package com.musicspring.app.music_app.artist.service;

import com.musicspring.app.music_app.artist.model.dto.ArtistRequest;
import com.musicspring.app.music_app.artist.model.dto.ArtistResponse;
import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongId;
import com.musicspring.app.music_app.artist.model.mapper.ArtistMapper;
import com.musicspring.app.music_app.artist.model.mapper.ArtistXSongMapper;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.service.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final ArtistXSongRepository artistXSongRepository;
    private final ArtistXSongMapper artistXSongMapper;
    private final SongService songService;
    private final SongMapper songMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper,
                         ArtistXSongRepository artistXSongRepository,
                         ArtistXSongMapper artistXSongMapper,
                         SongService songService,
                         SongMapper songMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.artistXSongRepository = artistXSongRepository;
        this.artistXSongMapper = artistXSongMapper;
        this.songService = songService;
        this.songMapper = songMapper;
    }

    public ArtistWithSongsResponse getArtistWithSongs(Long artistId) {
        ArtistEntity artist = findEntityById(artistId);
        List<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId);
        return artistMapper.toArtistWithSongsResponse(artist, relations);
    }

    @Transactional
    public void deleteById(Long id) {
        ArtistEntity artist = findEntityById(id);
        artist.setActive(false);
        artistRepository.save(artist);
    }

    public Page<ArtistResponse> getAllArtists(Pageable pageable) {
        return artistRepository.findByActiveTrue(pageable)
                .map(artistMapper::toResponse);
    }

    public ArtistResponse findById(Long id) {
        ArtistEntity artist = findEntityById(id);
        return artistMapper.toResponse(artist);
    }


    private ArtistEntity findEntityById(Long id) {
        return artistRepository.findByArtistIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist with id " + id + " not found"));
    }

    public boolean existsById(Long id) {
        return artistRepository.existsByArtistIdAndActiveTrue(id);
    }

    public ArtistResponse save(ArtistRequest artistRequest) {
        if (artistRequest == null) {
            throw new IllegalArgumentException("ArtistEntity cannot be null");
        }
       ArtistEntity artistEntity = artistMapper.toEntity(artistRequest);
        artistEntity.setActive(true);
        ArtistEntity saved = artistRepository.save(artistEntity);
        return artistMapper.toResponse(saved);
    }

    public List<ArtistResponse> findByName(String name) {
        List<ArtistEntity> artists = artistRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
        return artistMapper.toResponseList(artists);
    }

    @Transactional
    public ArtistXSongResponse createArtistSongRelationship(Long artistId, Long songId) {
        ArtistEntity artist = findEntityById(artistId);
        SongResponse songDto = songService.findById(songId);
        SongEntity song = songMapper.toEntity(songDto);

        ArtistXSongId id = new ArtistXSongId(artistId, songId);

        if (artistXSongRepository.existsById(id)) {
            throw new IllegalStateException("Relationship between artist " + artistId + " and song " + songId + " already exists");
        }

        ArtistXSongEntity relation = ArtistXSongEntity.builder()
                .artistXSongId(id)
                .artist(artist)
                .song(song)
                .build();

        ArtistXSongEntity saved = artistXSongRepository.save(relation);
        return artistXSongMapper.toResponse(saved);
    }

    @Transactional
    public void deleteArtistSongRelationship(Long artistId, Long songId) {
        ArtistXSongId id = new ArtistXSongId(artistId, songId);
        if (!artistXSongRepository.existsById(id)) {
            throw new EntityNotFoundException("Relationship between artist " + artistId + " and song " + songId + " not found");
        }
        artistXSongRepository.deleteById(id);
    }

    public Page<ArtistXSongResponse> getSongsByArtistId(Long artistId, Pageable pageable) {
        if (!existsById(artistId)) {
            throw new EntityNotFoundException("Artist with id " + artistId + " not found");
        }

        Page<ArtistXSongEntity> relations = artistXSongRepository.findByArtistArtistId(artistId, pageable);
        List<ArtistXSongResponse> dtoList = artistXSongMapper.toResponseList(relations.getContent());
        return new PageImpl<>(dtoList, pageable, relations.getTotalElements());
    }

    public Page<ArtistXSongResponse> getArtistsBySongId(Long songId, Pageable pageable) {
        if (!songService.existsById(songId)) {
            throw new EntityNotFoundException("Song with id " + songId + " not found");
        }

        Page<ArtistXSongEntity> relations = artistXSongRepository.findBySongSongId(songId, pageable);
        List<ArtistXSongResponse> dtoList = artistXSongMapper.toResponseList(relations.getContent());
        return new PageImpl<>(dtoList, pageable, relations.getTotalElements());
    }
}
