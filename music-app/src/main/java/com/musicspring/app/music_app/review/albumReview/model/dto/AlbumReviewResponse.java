package com.musicspring.app.music_app.review.albumReview.model.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AlbumReviewResponse {
    private String username;
    private Long userId;
    private String description;
    private Double rating;

}