package com.musicspring.app.music_app.reaction.reviewReaction.service;

import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import com.musicspring.app.music_app.reaction.reviewReaction.repository.ReviewReactionRepository;
import com.musicspring.app.music_app.shared.IService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewReactionService implements IService<ReviewReactionEntity> {

    private final ReviewReactionRepository reviewReactionRepository;

    @Autowired
    public  ReviewReactionService(ReviewReactionRepository reviewReactionRepository){
        this.reviewReactionRepository = reviewReactionRepository;
    }

    @Override
    public Page<ReviewReactionEntity> findAll(Pageable pageable) {
        return reviewReactionRepository.findAll(pageable);
    }

    @Override
    public ReviewReactionEntity findById(Long id) {
        return reviewReactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review reaction with ID: " + id + " was not found." ));
    }

    @Override
    public void deleteById(Long id) {
        reviewReactionRepository.deleteById(id);
    }

    @Override
    public ReviewReactionEntity save(ReviewReactionEntity reviewReactionEntity) {
        return reviewReactionRepository.save(reviewReactionEntity);
    }

    public Page<ReviewReactionEntity> findByAlbumReviewId(Long albumReviewId, Pageable pageable){
        return reviewReactionRepository.findByAlbumReview_ReviewId(albumReviewId, pageable);
    }

    public Page<ReviewReactionEntity> findBySongReviewId(Long songReviewId, Pageable pageable){
        return reviewReactionRepository.findBySongReview_ReviewId(songReviewId, pageable);
    }

    public Page<ReviewReactionEntity> findByUserId(Long userId, Pageable pageable){
        return reviewReactionRepository.findByUser_UserId(userId, pageable);
    }
}
