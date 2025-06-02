package com.musicspring.app.music_app.model.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileResponse {
    private Long id;
    private String username;
    private String biography;
    private String profilePictureUrl;

    private Long totalAlbumReviews;
    private Long totalSongReviews;
    private Double averageRating;
}