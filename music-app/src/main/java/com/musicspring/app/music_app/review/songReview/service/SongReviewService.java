package com.musicspring.app.music_app.review.songReview.service;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.review.songReview.repository.SongReviewRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.service.SongService;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Date;

@Service
public class SongReviewService {

    private final SongReviewRepository songReviewRepository;
    private final SongService songService;
    private final UserService userService;
    private final SongReviewMapper songReviewMapper;
    private final UserMapper userMapper;
    private final SongMapper songMapper;

    @Autowired
    public SongReviewService (SongReviewRepository songReviewRepository,UserService userService
            ,SongService songService,SongReviewMapper songReviewMapper, UserMapper userMapper,
                              SongMapper songMapper){
        this.songReviewRepository = songReviewRepository;
        this.userService = userService;
        this.songService = songService;
        this.songReviewMapper = songReviewMapper;
        this.userMapper = userMapper;
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
        // Validate input
        if (songReviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("UserId is required");
        }
        
        if (songReviewRequest.getSongId() == null && songReviewRequest.getSpotifyId() == null) {
            throw new IllegalArgumentException("Either songId or spotifyId is required");
        }

        UserResponse user = userService.findById(songReviewRequest.getUserId());
        SongResponse song = findOrCreateSong(songReviewRequest);

        UserEntity userEntity = userMapper.toUserEntity(user);
        SongEntity songEntity = songMapper.toEntity(song);
        
        SongReviewEntity songReviewEntity = songReviewMapper.toEntity(songReviewRequest, userEntity, songEntity);
        
        SongReviewEntity savedEntity = songReviewRepository.save(songReviewEntity);
        
        return songReviewMapper.toResponse(savedEntity);
    }

    private SongResponse findOrCreateSong(SongReviewRequest songReviewRequest) {
        // If songId is provided, use existing song
        if (songReviewRequest.getSongId() != null) {
            return songService.findById(songReviewRequest.getSongId());
        }
        
        // If spotifyId is provided, find or create song
        if (songReviewRequest.getSpotifyId() != null) {
            try {
                return songService.findBySpotifyId(songReviewRequest.getSpotifyId());
            } catch (EntityNotFoundException e) {
                return createNewSongFromSpotifyData(songReviewRequest);
            }
        }
        
        throw new IllegalArgumentException("Either songId or spotifyId must be provided");
    }

    private SongResponse createNewSongFromSpotifyData(SongReviewRequest songReviewRequest) {
        if (songReviewRequest.getSongName() == null || songReviewRequest.getArtistName() == null) {
            throw new IllegalArgumentException("songName and artistName are required when creating new song from Spotify data");
        }
        
        // Convert release date string to Date if provided
        Date releaseDate = null;
        if (songReviewRequest.getReleaseDate() != null) {
            try {
                releaseDate = java.sql.Date.valueOf(songReviewRequest.getReleaseDate());
            } catch (IllegalArgumentException e) {
                // Invalid date format, continue without date
                releaseDate = null;
            }
        }

        // Create song entity with Spotify data
        SongEntity newSong = SongEntity.builder()
                .spotifyId(songReviewRequest.getSpotifyId())
                .name(songReviewRequest.getSongName())
                .artistName(songReviewRequest.getArtistName())
                .albumName(songReviewRequest.getAlbumName())
                .imageUrl(songReviewRequest.getImageUrl())
                .durationMs(songReviewRequest.getDurationMs())
                .previewUrl(songReviewRequest.getPreviewUrl())
                .spotifyLink(songReviewRequest.getSpotifyLink())
                .releaseDate(releaseDate)
                .active(true)
                .build();
        
        return songService.saveSong(newSong);
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
