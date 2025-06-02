package com.musicspring.app.music_app.security.service;

import com.musicspring.app.music_app.security.dto.AuthRequest;
import com.musicspring.app.music_app.security.repository.CredentialRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CredentialRepository credentialsRepository;
    private final AuthenticationManager authenticationManager;
    
    public AuthService(CredentialRepository credentialsRepository,
                       AuthenticationManager authenticationManager) {
        this.credentialsRepository = credentialsRepository;
        this.authenticationManager = authenticationManager;
    }
    
    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return credentialsRepository.findByEmail(input.username()).orElseThrow();
    }
}