package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.SignupWithEmailRequest;
import com.musicspring.app.music_app.model.dto.UserProfileResponse;
import com.musicspring.app.music_app.model.dto.UserResponse;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.security.dto.AuthRequest;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {
    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .roles(user.getCredential() != null ? user.getCredential().getRoles() : Set.of())
                .profilePictureUrl(user.getCredential() != null ? user.getCredential().getProfilePictureUrl() : null)
                .biography(user.getCredential() != null ? user.getCredential().getBiography() : null)
                .build();
    }
    /// changed signuprequest for authrequest
    public UserEntity toUserEntity(AuthRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .active(true)
                .build();
    }
    public UserEntity toUserEntity (SignupWithEmailRequest signupRequest) {
        return UserEntity.builder()
                .username(signupRequest.getUsername())
                .active(true)
                .build();
    }
    public UserProfileResponse toUserProfile (UserEntity user) {
        return UserProfileResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .biography(user.getCredential() != null ? user.getCredential().getBiography() : null)
                .profilePictureUrl(user.getCredential() != null ? user.getCredential().getProfilePictureUrl() : null)
                .build();
    }

}
