package com.musicspring.app.music_app.model.dto;

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