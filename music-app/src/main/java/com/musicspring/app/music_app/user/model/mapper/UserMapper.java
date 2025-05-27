package com.musicspring.app.music_app.user.model.mapper;

import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse (UserEntity user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .roles(user.getCredential().getRoles())
                .build();
    }
    public UserEntity toUserEntity(SignupRequest request) {
        return UserEntity.builder()
                .username(request.getUsername())
                .active(true)
                .build();
    }
    public UserEntity toUserEntity(UserResponse userResponse) {
        return UserEntity.builder()
                .userId(userResponse.getId())
                .username(userResponse.getUsername())
                .active(true)
                .build();
    }

}
