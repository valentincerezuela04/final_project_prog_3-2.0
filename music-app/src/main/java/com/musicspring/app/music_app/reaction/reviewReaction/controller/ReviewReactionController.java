package com.musicspring.app.music_app.reaction.reviewReaction.controller;

import com.musicspring.app.music_app.reaction.reviewReaction.model.dto.ReviewReactionRequest;
import com.musicspring.app.music_app.reaction.reviewReaction.model.dto.ReviewReactionResponse;
import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import com.musicspring.app.music_app.reaction.reviewReaction.service.ReviewReactionService;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.service.AlbumReviewService;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.service.SongReviewService;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviewEntity-reactions")
public class ReviewReactionController {

    private final ReviewReactionService reviewReactionService;
    private final UserService userService;
    private final AlbumReviewService albumReviewService;
    private final SongReviewService songReviewService;
    private final UserMapper userMapper;

    @Autowired
    public ReviewReactionController(ReviewReactionService reviewReactionService,
                                    UserService userService,
                                    AlbumReviewService albumReviewService,
                                    SongReviewService songReviewService,
                                    UserMapper userMapper) {
        this.reviewReactionService = reviewReactionService;
        this.userService = userService;
        this.albumReviewService = albumReviewService;
        this.songReviewService = songReviewService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewReactionResponse>> getAllReviewReactions(Pageable pageable){
        Page<ReviewReactionEntity> page = reviewReactionService.findAll(pageable);
        Page<ReviewReactionResponse> responsePage = page.map(entity -> ReviewReactionResponse.of(entity, userMapper));
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewReactionResponse> getReviewReactionById(@PathVariable Long id){
        ReviewReactionEntity entity = reviewReactionService.findById(id);
        return ResponseEntity.ok(ReviewReactionResponse.of(entity, userMapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviewReaction(@PathVariable Long id){
        reviewReactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/album-reviewEntity/{reviewId}")
    public ResponseEntity<Page<ReviewReactionResponse>> getByAlbumReviewId(@PathVariable Long reviewId, Pageable pageable){
        Page<ReviewReactionEntity> page = reviewReactionService.findByAlbumReviewId(reviewId, pageable);
        Page<ReviewReactionResponse> responsePage = page.map(entity -> ReviewReactionResponse.of(entity, userMapper));
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/song-reviewEntity/{reviewId}")
    public ResponseEntity<Page<ReviewReactionResponse>> getBySongReviewId(@PathVariable Long reviewId, Pageable pageable){
        Page<ReviewReactionEntity> page = reviewReactionService.findBySongReviewId(reviewId, pageable);
        Page<ReviewReactionResponse> responsePage = page.map(entity -> ReviewReactionResponse.of(entity, userMapper));
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewReactionResponse>> getByUserId(@PathVariable Long userId, Pageable pageable){
        Page<ReviewReactionEntity> page = reviewReactionService.findByUserId(userId, pageable);
        Page<ReviewReactionResponse> responsePage = page.map(entity -> ReviewReactionResponse.of(entity, userMapper));
        return ResponseEntity.ok(responsePage);
    }

    /*@PostMapping
    public ResponseEntity<ReviewReactionResponse> createReviewReaction(@RequestBody @Valid ReviewReactionRequest request,
                                                                       @RequestParam Long userId){
        request.validate(); /// Validate request (only one of albumReviewId or songReviewId must be set)

        UserEntity userEntity = userService.findById(userId);

        AlbumReviewEntity albumReviewEntity = null;
        SongReviewEntity songReviewEntity = null;

        /// Load the related reviewEntity entity based on the request
        if (request.getAlbumReviewId() != null){
            albumReviewEntity = albumReviewService.findById(request.getAlbumReviewId());
        } else if (request.getSongReviewId() != null) {
            songReviewEntity = songReviewService.findById(request.getSongReviewId());
        }

        /// Convert DTO to entity
        ReviewReactionEntity entity = request.toEntity(userEntity, albumReviewEntity, songReviewEntity);
        ReviewReactionEntity saved = reviewReactionService.save(entity);

        ReviewReactionResponse response = ReviewReactionResponse.of(saved, userMapper);
        return ResponseEntity.ok(response);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<ReviewReactionResponse> updateReviewReaction(@PathVariable Long id,
                                                                       @RequestBody @Valid ReviewReactionRequest request){

        ReviewReactionEntity existing = reviewReactionService.findById(id);
        existing.setReactionType(request.getReactionType()); /// Update only the reaction type

        ReviewReactionEntity updated = reviewReactionService.save(existing);
        ReviewReactionResponse response = ReviewReactionResponse.of(updated, userMapper);
        return ResponseEntity.ok(response);
    }


}
