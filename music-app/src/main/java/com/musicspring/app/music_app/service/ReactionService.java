package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.request.ReactionRequest;
import com.musicspring.app.music_app.model.dto.response.ReactionResponse;
import com.musicspring.app.music_app.model.entity.CommentEntity;
import com.musicspring.app.music_app.model.entity.ReactionEntity;
import com.musicspring.app.music_app.model.entity.ReviewEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.model.enums.ReactedType;
import com.musicspring.app.music_app.model.enums.ReactionType;
import com.musicspring.app.music_app.model.mapper.ReactionMapper;
import com.musicspring.app.music_app.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final ReactionMapper reactionMapper;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository,
                           UserRepository userRepository,
                           CommentRepository commentRepository,
                           ReviewRepository reviewRepository,
                           ReactionMapper reactionMapper) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.reactionMapper = reactionMapper;
    }

    public Page<ReactionResponse> findAll(Pageable pageable){
        return reactionMapper.toResponsePage(reactionRepository.findAll(pageable));
    }

    public ReactionResponse findById(Long id){
        ReactionEntity reactionEntity = reactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reaction with ID:" + id + " not found."));
        return reactionMapper.toResponse(reactionEntity);
    }

    public Page<ReactionResponse> findByReviewId(Long reviewId, Pageable pageable) {
        Page<ReactionEntity> reactions = reactionRepository.findByReview_ReviewId(reviewId, pageable);
        return reactions.map(reactionMapper::toResponse);
    }

    public Page<ReactionResponse> findByCommentId(Long commentId, Pageable pageable) {
        Page<ReactionEntity> reactions = reactionRepository.findByComment_CommentId(commentId, pageable);
        return reactions.map(reactionMapper::toResponse);
    }

    public Page<ReactionResponse> findReactionsByTypeAndTarget(ReactionType reactionType, ReactedType reactedType, Pageable pageable) {
        Page<ReactionEntity> reactions = reactionRepository.findByReactionTypeAndReactedType(reactionType, reactedType, pageable);
        return reactions.map(reactionMapper::toResponse);
    }

    public Page<ReactionResponse> findReactionsByUserId(Long userId, Pageable pageable) {
        Page<ReactionEntity> reactions = reactionRepository.findByUser_UserId(userId, pageable);
        return reactions.map(reactionMapper::toResponse);
    }

    @Transactional
    public ReactionResponse createReaction(ReactionRequest request, Long reactedId) {
        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found."));

        if (request.getReactedType() == ReactedType.REVIEW) {
            ReviewEntity reviewEntity = reviewRepository.findById(reactedId)
                    .orElseThrow(() -> new EntityNotFoundException("Review with ID " + reactedId + " not found."));

            // Check if reaction already exists - fail if it does
            Optional<ReactionEntity> existingReaction = reactionRepository.findByUserAndReview(userEntity, reviewEntity);
            if (existingReaction.isPresent()) {
                throw new IllegalStateException("User already has a reaction on this review");
            }

            ReactionEntity reactionEntity = reactionMapper.toEntity(request, userEntity, reviewEntity);
            reactionRepository.save(reactionEntity);
            return reactionMapper.toResponse(reactionEntity);

        } else if (request.getReactedType() == ReactedType.COMMENT) {
            CommentEntity commentEntity = commentRepository.findById(reactedId)
                    .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + reactedId + " not found."));

            // Check if reaction already exists - fail if it does
            Optional<ReactionEntity> existingReaction = reactionRepository.findByUserAndComment(userEntity, commentEntity);
            if (existingReaction.isPresent()) {
                throw new IllegalStateException("User already has a reaction on this comment");
            }
            
            ReactionEntity reactionEntity = reactionMapper.toEntity(request, userEntity, commentEntity);
            reactionRepository.save(reactionEntity);
            return reactionMapper.toResponse(reactionEntity);

        } else {
            throw new IllegalArgumentException("Invalid ReactedType: " + request.getReactedType());
        }
    }

    @Transactional
    public ReactionResponse updateReaction(Long reactionId, ReactionType newReactionType) {
        ReactionEntity reactionEntity = reactionRepository.findById(reactionId)
                .orElseThrow(() -> new EntityNotFoundException("Reaction with ID " + reactionId + " not found."));
        
        reactionEntity.setReactionType(newReactionType);
        ReactionEntity updated = reactionRepository.save(reactionEntity);
        return reactionMapper.toResponse(updated);
    }

    @Transactional
    public void deleteReaction(Long reactionId) {
        // Idempotent - doesn't fail if reaction doesn't exist
        reactionRepository.deleteById(reactionId);
    }

}
