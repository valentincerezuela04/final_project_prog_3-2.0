package com.musicspring.app.music_app.review.songReview.service;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.review.songReview.repository.SongReviewRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.service.SongService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SongReviewService implements IService<SongReviewEntity> {

    private final SongReviewRepository songReviewRepository;
    private final SongService songService;
    private final UserService userService;
    private final SongReviewMapper songReviewMapper;

    @Autowired
    public SongReviewService (SongReviewRepository songReviewRepository,UserService userService,SongService songService,SongReviewMapper songReviewMapper){
        this.songReviewRepository = songReviewRepository;
        this.userService = userService;
        this.songService = songService;
        this.songReviewMapper = songReviewMapper;
    }

    @Override
    public Page<SongReviewEntity> findAll(Pageable pageable) {
        return songReviewRepository.findAll(pageable);
    }

    @Override
    public SongReviewEntity findById(Long id) {
        return songReviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Review with ID: " + id + " was not found.")); ///Reemplazar por una excepcion personalizada
    }

    @Override
    public void deleteById(Long id) {
        SongReviewEntity songReviewEntity = findById(id);
        songReviewEntity.setActive(false);
        songReviewRepository.save(songReviewEntity);
    }

    @Override
    public SongReviewEntity save(SongReviewEntity songReview) {
        return songReviewRepository.save(songReview);
    }

    public SongReviewEntity save(SongReviewRequest songReviewRequest) {
        // Buscar las entidades relacionadas
        UserEntity user = userService.findById(songReviewRequest.getUserId());
        SongEntity song = songService.findById(songReviewRequest.getSongId());

        // Crear la entidad usando el mapper
        SongReviewEntity songReviewEntity = songReviewMapper.toEntity(songReviewRequest, user, song);

        // Guardar la entidad en la base de datos
        return songReviewRepository.save(songReviewEntity);
    }

    public Page<SongReviewEntity> findBySongId (Long songId, Pageable pageable){
        return songReviewRepository.findBySong_Id(songId,pageable);
    }
    public Page<SongReviewEntity> findByUserId(Long songId, Pageable pageable){
        return songReviewRepository.findByUser_UserId(songId,pageable);
    }
}
