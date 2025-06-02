package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.repository.SongReviewRepository;
import com.musicspring.app.music_app.model.entity.SongEntity;
import com.musicspring.app.music_app.model.mapper.SongMapper;
import com.musicspring.app.music_app.repository.SongRepository;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SongReviewService {

    private final SongReviewRepository songReviewRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final SongReviewMapper songReviewMapper;
    private final SongMapper songMapper;

    @Autowired
    public SongReviewService (SongReviewRepository songReviewRepository, SongRepository songRepository, UserRepository userRepository
            , SongReviewMapper songReviewMapper,
                              SongMapper songMapper){
        this.songReviewRepository = songReviewRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.songReviewMapper = songReviewMapper;
        this.songMapper = songMapper;
    }

    public Page<SongReviewResponse> findAll(Pageable pageable) {
        return songReviewMapper.toResponsePage(songReviewRepository.findAll(pageable));
    }

    public SongReviewResponse findById(Long id) {
        return songReviewMapper.toResponse(songReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song review with ID: " + id + " was not found.")));
    }

    public void deleteById(Long id) {
        SongReviewEntity songReviewEntity = songReviewRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song review with ID: " + id + " was not found."));
        songReviewEntity.setActive(false);
        songReviewRepository.save(songReviewEntity);
    }


    public SongReviewResponse createSongReview(SongReviewRequest songReviewRequest) {
        if (songReviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("UserId is required");
        }
        
        if (songReviewRequest.getSongId() == null && songReviewRequest.getSpotifyId() == null) {
            throw new IllegalArgumentException("Either songId or spotifyId is required");
        }


        UserEntity userEntity = userRepository.findById(songReviewRequest.getUserId())
                .orElseThrow(EntityNotFoundException::new);
        SongEntity songEntity = findOrCreateSongEntity(songReviewRequest);
        
        SongReviewEntity songReviewEntity = songReviewMapper.toEntity(songReviewRequest, userEntity, songEntity);
        
        SongReviewEntity savedEntity = songReviewRepository.save(songReviewEntity);
        
        return songReviewMapper.toResponse(savedEntity);
    }

    private SongEntity findOrCreateSongEntity(SongReviewRequest songReviewRequest) {
        // If songId is provided, use existing song entity
        if (songReviewRequest.getSongId() != null) {
            return songRepository.findById(songReviewRequest.getSongId())
                    .orElseThrow(() -> new EntityNotFoundException("Song with ID: " +
                            songReviewRequest.getSongId() + " was not found."));
        }

        if (songReviewRequest.getSpotifyId() != null) {
            return songRepository.findBySpotifyId(songReviewRequest.getSpotifyId())
                    .orElseGet(() -> createNewSongEntityFromSpotifyData(songReviewRequest));
        }
        
        throw new IllegalArgumentException("Either songId or spotifyId must be provided");
    }

    private SongEntity createNewSongEntityFromSpotifyData(SongReviewRequest songReviewRequest) {
        if (songReviewRequest.getSongName() == null || songReviewRequest.getArtistName() == null) {
            throw new IllegalArgumentException("songName and artistName are required when creating new song from Spotify data");
        }


        SongEntity newSong = songMapper.toEntityFromReview(songReviewRequest);
        
        return songRepository.save(newSong);
    }

    public Page<SongReviewResponse> findBySongId (Long songId, Pageable pageable){
        return songReviewMapper.toResponsePage(songReviewRepository.findBySong_Id(songId,pageable));
    }
    
    public Page<SongReviewResponse> findBySpotifyId(String spotifyId, Pageable pageable) {
        return songReviewMapper.toResponsePage(songReviewRepository.findBySong_SpotifyId(spotifyId, pageable));
    }
    
    public Page<SongReviewResponse> findByUserId(Long songId, Pageable pageable){
        return songReviewMapper.toResponsePage(songReviewRepository.findByUser_UserId(songId,pageable));
    }
}
