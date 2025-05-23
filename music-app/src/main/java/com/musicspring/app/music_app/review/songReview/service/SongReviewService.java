package com.musicspring.app.music_app.review.songReview.service;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.review.songReview.repository.SongReviewRepository;
import com.musicspring.app.music_app.song.model.dto.SongRequest;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.service.SongService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SongReviewService {

    private final SongReviewRepository songReviewRepository;
    private final SongService songService;
    private final UserService userService;
    private final SongReviewMapper songReviewMapper;
    private final SongMapper songMapper;

    @Autowired
    public SongReviewService (SongReviewRepository songReviewRepository, UserService userService,
            SongService songService, SongReviewMapper songReviewMapper, SongMapper songMapper){
        this.songReviewRepository = songReviewRepository;
        this.userService = userService;
        this.songService = songService;
        this.songReviewMapper = songReviewMapper;
        this.songMapper = songMapper;
    }

    public Page<SongReviewResponse> findAll(Pageable pageable) {
        return songReviewMapper.toResponsePage(songReviewRepository.findAll(pageable));
    }

    public SongReviewResponse findById(Long id) {
        return songReviewMapper.toResponse(songReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Review with ID: " + id + " was not found.")));
    }

    public void deleteById(Long id) {
        SongReviewEntity songReviewEntity = songReviewRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Song Review with ID: " + id + " was not found."));
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

        UserEntity userEntity = userService.findEntityById(songReviewRequest.getUserId());
        SongEntity songEntity = findOrCreateSongEntity(songReviewRequest);
        
        SongReviewEntity songReviewEntity = songReviewMapper.toEntity(songReviewRequest, userEntity, songEntity);
        
        SongReviewEntity savedEntity = songReviewRepository.save(songReviewEntity);
        
        return songReviewMapper.toResponse(savedEntity);
    }

    private SongEntity findOrCreateSongEntity(SongReviewRequest songReviewRequest) {
        if (songReviewRequest.getSongId() != null) {
            return songService.findEntityById(songReviewRequest.getSongId());
        }

        if (songReviewRequest.getSpotifyId() != null) {
            if (songService.existsBySpotifyId(songReviewRequest.getSpotifyId())) {
                return songService.findEntityBySpotifyId(songReviewRequest.getSpotifyId());
            } else {
                return createNewSongEntityFromSpotifyData(songReviewRequest);
            }
        }
        
        throw new IllegalArgumentException("Either songId or spotifyId must be provided");
    }

    private SongEntity createNewSongEntityFromSpotifyData(SongReviewRequest songReviewRequest) {
        if (songReviewRequest.getSongName() == null || songReviewRequest.getArtistName() == null) {
            throw new IllegalArgumentException("songName and artistName are required when creating new song from Spotify data");
        }

        SongRequest songRequest = songMapper.toRequestFromReviewRequest(songReviewRequest);
        
        return songService.saveSongEntity(songRequest);
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
