package com.musicspring.app.music_app.model.dto.request;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileUpdateRequest {
    private String profilePictureUrl;
    private String biography;
}