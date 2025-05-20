package com.musicspring.app.music_app.review.albumReview.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.service.AlbumService;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumReviewService {
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumService albumService;
    private final UserService userService;
    private final AlbumReviewMapper albumReviewMapper;
    private final SongReviewMapper songReviewMapper;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository,
                              UserService userService,
                              AlbumService albumService,
                              AlbumReviewMapper albumReviewMapper, SongReviewMapper songReviewMapper) {
        this.albumReviewRepository = albumReviewRepository;
        this.userService = userService;
        this.albumService = albumService;
        this.albumReviewMapper = albumReviewMapper;
        this.songReviewMapper = songReviewMapper;
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
        // Buscar las entidades relacionadas
        UserEntity user = userService.findById(albumReviewRequest.getUserId());
        AlbumEntity album = albumService.findById(albumReviewRequest.getAlbumId());

        // Crear la entidad usando el mapper
        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, user, album);

        // Guardar la entidad en la base de datos
        return albumReviewMapper.toResponse(albumReviewRepository.save(albumReviewEntity));
    }

    public Page<AlbumReviewResponse> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_AlbumId(albumId, pageable));
    }

    public Page<AlbumReviewResponse> findByUserId(Long userId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByUser_UserId(userId, pageable));
    }
}
