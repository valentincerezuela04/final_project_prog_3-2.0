package com.musicspring.app.music_app.user.model.dto;

import com.musicspring.app.music_app.security.entities.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
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
    private AuthProvider authProvider;
    private String email;
}