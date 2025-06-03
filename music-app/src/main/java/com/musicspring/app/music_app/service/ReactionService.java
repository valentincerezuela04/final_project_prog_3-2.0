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
    public void deleteReactionToReview(Long userId, Long reviewId) {
        ReactionEntity reaction = reactionRepository
                .findByUser_UserIdAndReview_ReviewIdAndReactedType(userId, reviewId, ReactedType.REVIEW)
                .orElseThrow(() -> new EntityNotFoundException("Reaction by user " + userId + " on review " + reviewId + " not found."));
        reactionRepository.delete(reaction);
    }

    @Transactional
    public void deleteReactionToComment(Long userId, Long commentId) {
        ReactionEntity reaction = reactionRepository
                .findByUser_UserIdAndComment_CommentIdAndReactedType(userId, commentId, ReactedType.COMMENT)
                .orElseThrow(() -> new EntityNotFoundException("Reaction by user " + userId + " on comment " + commentId + " not found."));
        reactionRepository.delete(reaction);
    }

    @Transactional
    public ReactionResponse createReaction(ReactionRequest request, Long reactedId) {

        UserEntity userEntity = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + request.getUserId() + " not found."));

        if (request.getReactedType() == ReactedType.REVIEW) {

            ReviewEntity reviewEntity = reviewRepository.findById(reactedId)
                    .orElseThrow(() -> new EntityNotFoundException("Review with ID " + reactedId + " not found."));

            boolean exists = reactionRepository.existsByUserAndReviewAndReactionType(userEntity, reviewEntity, request.getReactionType());
            if (exists) {
                throw new IllegalStateException("User has already reacted with " + request.getReactionType() + " to this review.");
            }

            ReactionEntity reactionEntity = reactionMapper.toEntity(request, userEntity, reviewEntity);
            reactionRepository.save(reactionEntity);
            return reactionMapper.toResponse(reactionEntity);

        } else if (request.getReactedType() == ReactedType.COMMENT) {
            CommentEntity commentEntity = commentRepository.findById(reactedId)
                    .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + reactedId + " not found."));

            boolean exists = reactionRepository.existsByUserAndCommentAndReactionType(userEntity, commentEntity, request.getReactionType());
            if (exists) {
                throw new IllegalStateException("User has already reacted with " + request.getReactionType() + " to this comment.");
            }

            ReactionEntity reactionEntity = reactionMapper.toEntity(request, userEntity, commentEntity);
            reactionRepository.save(reactionEntity);
            return reactionMapper.toResponse(reactionEntity);

        } else {
            throw new IllegalArgumentException("Invalid ReactedType: " + request.getReactedType());
        }
    }

    @Transactional
    public ReactionResponse updateReactionType(Long userId, Long reactedId, ReactedType reactedType, ReactionType newReactionType) {
        ReactionEntity existingReaction;

        if (reactedType == ReactedType.REVIEW) {
            existingReaction = reactionRepository
                    .findByUser_UserIdAndReview_ReviewIdAndReactedType(userId, reactedId, reactedType)
                    .orElseThrow(() -> new EntityNotFoundException("No existing reaction found for this user and review"));
        } else if (reactedType == ReactedType.COMMENT) {
            existingReaction = reactionRepository
                    .findByUser_UserIdAndComment_CommentIdAndReactedType(userId, reactedId, reactedType)
                    .orElseThrow(() -> new EntityNotFoundException("No existing reaction found for this user and comment"));
        } else {
            throw new IllegalArgumentException("Invalid ReactedType: " + reactedType);
        }

        existingReaction.setReactionType(newReactionType);
        ReactionEntity updated = reactionRepository.save(existingReaction);
        return reactionMapper.toResponse(updated);
    }

}
