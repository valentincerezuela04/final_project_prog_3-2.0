package com.musicspring.app.music_app.security.service;

import com.musicspring.app.music_app.security.dto.AuthRequest;
import com.musicspring.app.music_app.security.dto.AuthResponse;
import com.musicspring.app.music_app.security.entity.CredentialEntity;
import com.musicspring.app.music_app.security.repository.CredentialRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CredentialRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(CredentialRepository credentialsRepository,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    
    public CredentialEntity authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }

    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        CredentialEntity credentialEntity = credentialsRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if(!credentialEntity.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh token does not match");
        }

        if (!jwtService.validateRefreshToken(refreshToken,credentialEntity)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String newAccessToken = jwtService.generateToken(credentialEntity);
        String newRefreshToken = jwtService.generateRefreshToken(credentialEntity);
        credentialEntity.setRefreshToken(newRefreshToken);
        credentialsRepository.save(credentialEntity);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}