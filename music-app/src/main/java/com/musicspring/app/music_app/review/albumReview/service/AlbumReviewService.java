package com.musicspring.app.music_app.review.albumReview.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.repository.AlbumRepository;
import com.musicspring.app.music_app.album.service.AlbumService;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.repository.UserRepository;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumReviewService {
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumReviewMapper albumReviewMapper;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository,
                              AlbumReviewMapper albumReviewMapper,
                              UserRepository userRepository,
                              AlbumRepository albumRepository) {
        this.albumReviewRepository = albumReviewRepository;
        this.albumReviewMapper = albumReviewMapper;
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;

    }


    public Page<AlbumReviewResponse> findAll(Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findAll(pageable));
    }


    public AlbumReviewResponse findById(Long id) {
        return albumReviewMapper.toResponse(albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album Review with ID: " + id + " was not found.")));
    }


    public void deleteById(Long id) {
        AlbumReviewEntity albumReviewEntity = albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album Review with ID: " + id + " was not found."));
        albumReviewEntity.setActive(false);
        albumReviewRepository.save(albumReviewEntity);
    }

    public AlbumReviewResponse createAlbumReview (AlbumReviewRequest albumReviewRequest) {

        UserEntity user = userRepository.findById(albumReviewRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + albumReviewRequest.getUserId() + " was not found."));
        AlbumEntity album = albumRepository.findById(albumReviewRequest.getAlbumId())
                .orElseThrow(() -> new EntityNotFoundException("Album with ID: " + albumReviewRequest.getAlbumId() + " was not found."));

        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, user, album);

        return albumReviewMapper.toResponse(albumReviewRepository.save(albumReviewEntity));
    }

    public Page<AlbumReviewResponse> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_AlbumId(albumId, pageable));
    }

    public Page<AlbumReviewResponse> findByUserId(Long userId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByUser_UserId(userId, pageable));
    }
}
