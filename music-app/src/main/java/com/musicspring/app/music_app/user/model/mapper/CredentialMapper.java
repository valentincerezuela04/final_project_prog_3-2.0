package com.musicspring.app.music_app.user.model.mapper;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CredentialMapper {
    public CredentialEntity toCredentialEntity (SignupRequest signupRequest, UserEntity user) {
        return CredentialEntity.builder()
                .password(signupRequest.getPassword())
                .user(user)
                .build();
    }

}
