package com.musicspring.app.music_app.model.dto.response;

import com.musicspring.app.music_app.security.entity.RoleEntity;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserResponse {
    private Long id;
    private String username;
    private Set<RoleEntity> roles;
    private String profilePictureUrl;
    private String biography;
}