package com.musicspring.app.music_app.security.oauth2.services;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.oauth2.dto.CustomOAuth2User;
import com.musicspring.app.music_app.security.repositories.CredentialRepository;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;

    @Autowired
    public CustomOAuth2UserService(CredentialRepository credentialRepository,
                                   UserRepository userRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        return processOAuth2User(oauth2User);
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        String googleId = oauth2User.getAttribute("sub");
        String name = oauth2User.getAttribute("name");
        String pictureUrl = oauth2User.getAttribute("picture");

        Optional<CredentialEntity> credentialOptional = credentialRepository.findByEmail(email);
        CredentialEntity credential;

        if (credentialOptional.isPresent()) {
            credential = credentialOptional.get();
            credential.setProvider(AuthProvider.GOOGLE);
            credential.setProviderId(googleId);
            credential.setProfilePictureUrl(pictureUrl);
        } else {
            credential = createNewOAuth2User(email, googleId, name, pictureUrl);
        }

        credentialRepository.save(credential);
        return new CustomOAuth2User(oauth2User.getAttributes(), credential);
    }

    private CredentialEntity createNewOAuth2User(String email, String googleId, String name, String pictureUrl) {
        UserEntity user = UserEntity.builder()
                .username(generateUsernameFromEmail(email))
                .active(true)
                .build();

        user = userRepository.save(user);

        return CredentialEntity.builder()
                .email(email)
                .provider(AuthProvider.GOOGLE)
                .providerId(googleId)
                .profilePictureUrl(pictureUrl)
                .user(user)
                .roles(new HashSet<>())
                .build();
    }

    private String generateUsernameFromEmail(String email) {
        return email.split("@")[0] + "_" + System.currentTimeMillis();
    }
}
