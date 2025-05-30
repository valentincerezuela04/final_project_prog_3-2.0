package com.musicspring.app.music_app.user.model.mapper;

import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.SignupWithEmailRequest;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
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
    public UserEntity toUserEntity(SignupRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .active(true)
                .build();
    }
    public UserEntity toUserEntity (SignupWithEmailRequest signupRequest) {
        return UserEntity.builder()
                .username(signupRequest.getUsername())
                .active(true)
                .build();
    }

}
