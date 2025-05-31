package com.musicspring.app.music_app.user.model.dto;

import com.musicspring.app.music_app.security.enums.AuthProvider;
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
    private AuthProvider authProvider;
    private String email;

    private Long totalAlbumReviews;
    private Long totalSongReviews;
    private Double averageRating;
}