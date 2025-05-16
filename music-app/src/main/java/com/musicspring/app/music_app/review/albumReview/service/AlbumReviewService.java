package com.musicspring.app.music_app.review.albumReview.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.service.AlbumService;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumReviewService implements IService<AlbumReviewEntity> {
    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumService albumService;
    private final UserService userService;
    private final AlbumReviewMapper albumReviewMapper;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository,
                              UserService userService,
                              AlbumService albumService,
                              AlbumReviewMapper albumReviewMapper) {
        this.albumReviewRepository = albumReviewRepository;
        this.userService = userService;
        this.albumService = albumService;
        this.albumReviewMapper = albumReviewMapper;
    }

    @Override
    public Page<AlbumReviewEntity> findAll(Pageable pageable) {
        return albumReviewRepository.findAll(pageable);
    }

    @Override
    public AlbumReviewEntity findById(Long id) {
        return albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album Review with ID: " + id + " was not found."));
    }

    @Override
    public void deleteById(Long id) {
        AlbumReviewEntity albumReviewEntity = findById(id);
        albumReviewEntity.setActive(false);
        albumReviewRepository.save(albumReviewEntity);
    }

    @Override
    public AlbumReviewEntity save(AlbumReviewEntity albumReview) {
        return albumReviewRepository.save(albumReview);
    }

    public AlbumReviewEntity save(AlbumReviewRequest albumReviewRequest) {
        // Buscar las entidades relacionadas
        UserEntity user = userService.findById(albumReviewRequest.getUserId());
        AlbumEntity album = albumService.findById(albumReviewRequest.getAlbumId());

        // Crear la entidad usando el mapper
        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, user, album);

        // Guardar la entidad en la base de datos
        return albumReviewRepository.save(albumReviewEntity);
    }

    public Page<AlbumReviewEntity> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewRepository.findByAlbum_AlbumId(albumId, pageable);
    }

    public Page<AlbumReviewEntity> findByUserId(Long userId, Pageable pageable) {
        return albumReviewRepository.findByUser_UserId(userId, pageable);
    }
}
