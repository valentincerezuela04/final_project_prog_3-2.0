package com.musicspring.app.music_app.security.oauth2.handlers;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.entities.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.enums.Role;
import com.musicspring.app.music_app.security.repositories.CredentialRepository;
import com.musicspring.app.music_app.security.repositories.RoleRepository;
import com.musicspring.app.music_app.security.services.JwtService;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public OAuth2AuthenticationSuccessHandler(JwtService jwtService, 
                                             CredentialRepository credentialRepository,
                                             UserRepository userRepository,
                                             RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        String email = null;
        String name = null;

        if (principal instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
            email = oidcUser.getEmail();
            name = oidcUser.getFullName();
        } else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            email = oauth2User.getAttribute("email");
            name = oauth2User.getAttribute("name");
        }

        if (email == null) {
            throw new RuntimeException("Email not found in OAuth2 response");
        }

        CredentialEntity credential = findOrCreateOAuth2User(email, name);
        String token = jwtService.generateToken(credential);

        // Use hardcoded redirect URL for now
        String redirectUri = "http://localhost:3000/oauth2/redirect";
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private CredentialEntity findOrCreateOAuth2User(String email, String name) {
        return credentialRepository.findByEmail(email)
                .orElseGet(() -> createOAuth2User(email, name));
    }

    private CredentialEntity createOAuth2User(String email, String name) {
        UserEntity user = UserEntity.builder()
                .username(name != null ? name : email.split("@")[0])
                .active(true)
                .build();
        
        user = userRepository.save(user);

        CredentialEntity credential = CredentialEntity.builder()
                .email(email)
                .provider(AuthProvider.GOOGLE)
                .user(user)
                .roles(getDefaultRoles())
                .build();

        return credentialRepository.save(credential);
    }
    
    private Set<RoleEntity> getDefaultRoles() {
        Set<RoleEntity> roles = new HashSet<>();
        roleRepository.findByRole(Role.ROLE_USER)
                .ifPresent(roles::add);
        return roles;
    }
}