package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.request.AlbumReviewRequest;
import com.musicspring.app.music_app.model.dto.response.AlbumReviewResponse;
import com.musicspring.app.music_app.model.entity.AlbumEntity;
import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.repository.AlbumRepository;
import com.musicspring.app.music_app.repository.AlbumReviewRepository;
import com.musicspring.app.music_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlbumReviewService {

    private final AlbumReviewRepository albumReviewRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final AlbumReviewMapper albumReviewMapper;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumReviewService(AlbumReviewRepository albumReviewRepository, AlbumRepository albumRepository, UserRepository userRepository, AlbumReviewMapper albumReviewMapper, AlbumMapper albumMapper) {
        this.albumReviewRepository = albumReviewRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.albumReviewMapper = albumReviewMapper;
        this.albumMapper = albumMapper;
    }


    public Page<AlbumReviewResponse> findAll(Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findAll(pageable));
    }

    public AlbumReviewResponse findById(Long id) {
        return albumReviewMapper.toResponse(albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album review with ID: " + id + " not found.")));
    }

    public void deleteById(Long id) {
        AlbumReviewEntity albumReviewEntity = albumReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album review with ID: " + id + " not found."));
        albumReviewEntity.setActive(false);
        albumReviewRepository.save(albumReviewEntity);
    }

    public AlbumReviewResponse createAlbumReview (AlbumReviewRequest albumReviewRequest) {
        if (albumReviewRequest.getUserId() == null) {
            throw new IllegalArgumentException("UserId is required");
        }

        if (albumReviewRequest.getAlbumId() == null && albumReviewRequest.getSpotifyId() == null) {
            throw new IllegalArgumentException("Either albumId or spotifyId is required");
        }

        UserEntity userEntity = userRepository.findById(albumReviewRequest.getUserId())
                .orElseThrow(EntityNotFoundException::new);
        AlbumEntity albumEntity = findOrCreateAlbumEntity(albumReviewRequest);

        AlbumReviewEntity albumReviewEntity = albumReviewMapper.toEntity(albumReviewRequest, userEntity, albumEntity);

        AlbumReviewEntity savedEntity = albumReviewRepository.save(albumReviewEntity);

        return albumReviewMapper.toResponse(savedEntity);
    }

    private AlbumEntity findOrCreateAlbumEntity (AlbumReviewRequest albumReviewRequest){
        if (albumReviewRequest.getAlbumId() != null) {
            return albumRepository.findById(albumReviewRequest.getAlbumId())
                    .orElseThrow(()-> new EntityNotFoundException("Album with ID " +
                            albumReviewRequest.getAlbumId() + " not found."));
        }

        if (albumReviewRequest.getSpotifyId() != null){
            return albumRepository.findBySpotifyId(albumReviewRequest.getSpotifyId())
                    .orElseGet(() -> createNewAlbumEntityFromSpotifyData(albumReviewRequest));
        }

        throw new IllegalArgumentException("Either albumId or spotifyId must be provided");
    }

    private AlbumEntity createNewAlbumEntityFromSpotifyData (AlbumReviewRequest albumReviewRequest) {
        if (albumReviewRequest.getAlbumName() == null || albumReviewRequest.getArtistName() == null){
            throw  new IllegalArgumentException("AlbumName and ArtistName are required when creating new album from Spotify data");
        }

        LocalDate releaseDate = null;

        if(albumReviewRequest.getReleaseDate() != null){
            releaseDate = java.sql.Date.valueOf(albumReviewRequest.getReleaseDate()).toLocalDate();
        }

        AlbumEntity newAlbum = albumMapper.toEntityFromReview(albumReviewRequest, releaseDate);

        return albumRepository.save(newAlbum);
    }

    public Page<AlbumReviewResponse> findByAlbumId(Long albumId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_AlbumId(albumId, pageable));
    }

    public Page<AlbumReviewResponse> findBySpotifyId (String spotifyId, Pageable pageable){
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByAlbum_SpotifyId(spotifyId,pageable));
    }

    public Page<AlbumReviewResponse> findByUserId(Long userId, Pageable pageable) {
        return albumReviewMapper.toResponsePage(albumReviewRepository.findByUser_UserId(userId, pageable));
    }
}
