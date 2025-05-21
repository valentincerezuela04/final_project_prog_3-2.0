package com.musicspring.app.music_app.review.songReview.service;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.review.songReview.repository.SongReviewRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.repository.SongRepository;
import com.musicspring.app.music_app.song.service.SongService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.repository.UserRepository;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SongReviewService {

    private final SongReviewRepository songReviewRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final SongReviewMapper songReviewMapper;


    @Autowired
    public SongReviewService (SongReviewRepository songReviewRepository,SongReviewMapper songReviewMapper,UserRepository userRepository,SongRepository songRepository){
        this.songReviewRepository = songReviewRepository;
        this.songReviewMapper = songReviewMapper;
        this.userRepository = userRepository;
        this.songRepository = songRepository;

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

        UserEntity user = userRepository.findById(songReviewRequest.getUserId())
                .orElseThrow(() ->(new EntityNotFoundException("User with ID: " + songReviewRequest.getUserId() + " was not found.")));
        SongEntity song = songRepository.findById(songReviewRequest.getSongId())
                .orElseThrow(() ->(new EntityNotFoundException("Song with ID: " + songReviewRequest.getSongId() + " was not found.")));

        SongReviewEntity songReviewEntity = songReviewMapper.toEntity(songReviewRequest, user, song);

        return songReviewMapper.toResponse(songReviewRepository.save(songReviewEntity));
    }

    public Page<SongReviewResponse> findBySongId (Long songId, Pageable pageable){
        return songReviewMapper.toResponsePage(songReviewRepository.findBySong_Id(songId,pageable));
    }
    public Page<SongReviewResponse> findByUserId(Long songId, Pageable pageable){
        return songReviewMapper.toResponsePage(songReviewRepository.findByUser_UserId(songId,pageable));
    }
}
