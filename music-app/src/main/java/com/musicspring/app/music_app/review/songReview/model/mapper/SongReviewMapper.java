package com.musicspring.app.music_app.review.songReview.model.mapper;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import org.springframework.stereotype.Component;

@Component
public class SongReviewMapper {

    public static SongReviewResponse toResponse(SongReviewEntity songReview) {
        return SongReviewResponse.builder()
                .username(songReview.getUser().getUsername())
                .rating(songReview.getRating())
                .description(songReview.getDescription())
                .build();
    }

    public static SongReviewEntity toModel()
}
