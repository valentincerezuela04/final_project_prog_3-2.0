package com.musicspring.app.music_app.security.oauth2.dto;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final Map<String, Object> attributes;
    @Getter
    private final CredentialEntity credential;

    public CustomOAuth2User(Map<String, Object> attributes, CredentialEntity credential) {
        this.attributes = attributes;
        this.credential = credential;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // preguntar a edu
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return credential.getAuthorities();
    }

    @Override
    public String getName() {
        return credential.getEmail();
    }

}
