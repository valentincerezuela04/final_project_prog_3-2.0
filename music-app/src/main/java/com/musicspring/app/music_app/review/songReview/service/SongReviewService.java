package com.musicspring.app.music_app.review.songReview.service;

import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.repository.SongReviewRepository;
import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SongReviewService implements IService<SongReviewEntity> {

    private final SongReviewRepository songReviewRepository;
    public SongReviewService (SongReviewRepository songReviewRepository){
        this.songReviewRepository = songReviewRepository;
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

    public Page<SongReviewEntity> findBySongId (Long songId, Pageable pageable){
        return songReviewRepository.findBySong_Id(songId,pageable);
    }
}
