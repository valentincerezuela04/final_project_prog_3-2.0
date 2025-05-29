package com.musicspring.app.music_app.user.model.mapper;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.entities.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.SignupWithEmailRequest;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import org.springframework.stereotype.Component;


import java.util.Set;

import static com.musicspring.app.music_app.security.enums.Role.ROLE_USER;

@Component
public class CredentialMapper {
    public CredentialEntity toCredentialEntity (SignupWithEmailRequest signupRequest, UserEntity user) {
        return CredentialEntity.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .provider(AuthProvider.LOCAL)
                .user(user)
                .roles(null)
                .build();
    }
    public CredentialEntity toCredentialEntity (SignupRequest signupRequest, UserEntity user) {
        return CredentialEntity.builder()
                .email(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .provider(AuthProvider.LOCAL)
                .user(user)
                .roles(null)
                .build();
    }

}
