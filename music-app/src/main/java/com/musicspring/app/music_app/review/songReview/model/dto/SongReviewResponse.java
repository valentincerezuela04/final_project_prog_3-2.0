package com.musicspring.app.music_app.review.songReview.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SongReviewResponse {
    private Long songReviewId;

    private String username;

    private Long userId;

    private String description;

    private Double rating;


}