package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.repository.AlbumRepository;
import com.musicspring.app.music_app.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumReviewService {
    private final AlbumReviewRepository albumReviewRepository;
    //    private final AlbumService albumService;
    //    private final UserService userService;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final AlbumReviewMapper albumReviewMapper;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository,
                              AlbumRepository albumRepository, UserRepository userRepository,
                              AlbumReviewMapper albumReviewMapper) {
        this.albumReviewRepository = albumReviewRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.albumReviewMapper = albumReviewMapper;
    }


    public Page<AlbumReviewResponse> findAll(Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findAll(pageable));
    }


    public AlbumReviewResponse findById(Long id) {
        return albumReviewMapper.toResponse(albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album review with ID: " + id + " was not found.")));
    }


    public void deleteById(Long id) {
        AlbumReviewEntity albumReviewEntity = albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album review with ID: " + id + " was not found."));
        albumReviewEntity.setActive(false);
        albumReviewRepository.save(albumReviewEntity);
    }

    public AlbumReviewResponse createAlbumReview(AlbumReviewRequest albumReviewRequest) {
        // Get managed entities directly from repositories
//        var userEntity = userService.findEntityById(albumReviewRequest.getUserId());
        var userEntity = userRepository.findById(albumReviewRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with ID " + albumReviewRequest.getUserId() + " was not found."));
//        var albumEntity = albumService.findEntityById(albumReviewRequest.getAlbumId());
        var albumEntity = albumRepository.findById(albumReviewRequest.getAlbumId()).orElseThrow(() -> new EntityNotFoundException("Album with ID " + albumReviewRequest.getAlbumId() + " was not found."));

        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, userEntity, albumEntity);

        return albumReviewMapper.toResponse(albumReviewRepository.save(albumReviewEntity));
    }

    public Page<AlbumReviewResponse> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_id(albumId, pageable));
    }

    public Page<AlbumReviewResponse> findByUserId(Long userId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByUser_UserId(userId, pageable));
    }
}
