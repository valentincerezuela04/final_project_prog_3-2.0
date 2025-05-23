package com.musicspring.app.music_app.review.albumReview.service;

import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.service.AlbumService;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.repository.ArtistRepository;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository,
                              UserService userService,
                              AlbumService albumService,
                              AlbumReviewMapper albumReviewMapper, SongReviewMapper songReviewMapper,
                              UserMapper userMapper, AlbumMapper albumMapper, ArtistRepository artistRepository) {
        this.albumReviewRepository = albumReviewRepository;
        this.userService = userService;
        this.albumService = albumService;
        this.albumReviewMapper = albumReviewMapper;
        this.songReviewMapper = songReviewMapper;
        this.userMapper = userMapper;
        this.albumMapper = albumMapper;
        this.artistRepository = artistRepository;
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
        UserResponse user = userService.findById(albumReviewRequest.getUserId());
        AlbumResponse album = albumService.findById(albumReviewRequest.getAlbumId());
        ArtistEntity artistEntity = artistRepository.findById(album.getArtistId())
                .orElseThrow(()  -> new EntityNotFoundException("Artist with ID: " + album.getArtistId() + " was not found."));

        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, userMapper.toUserEntity(user), albumMapper.responseToEntity(album, artistEntity));

        return albumReviewMapper.toResponse(albumReviewRepository.save(albumReviewEntity));
    }

    public Page<AlbumReviewResponse> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_AlbumId(albumId, pageable));
    }

    public Page<AlbumReviewResponse> findByUserId(Long userId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByUser_UserId(userId, pageable));
    }
}
