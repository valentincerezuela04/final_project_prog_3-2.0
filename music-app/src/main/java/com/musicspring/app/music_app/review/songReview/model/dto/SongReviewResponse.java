package com.musicspring.app.music_app.review.songReview.model.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SongReviewResponse {
    ///private Long songReviewId;
    private String username;
    private Long userId;
    ///private Long songId;
    private String description;
    private Double rating;

}