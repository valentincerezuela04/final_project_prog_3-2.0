package com.musicspring.app.music_app.security.controller;

import com.musicspring.app.music_app.security.dto.AuthRequest;
import com.musicspring.app.music_app.security.dto.AuthResponse;
import com.musicspring.app.music_app.security.dto.RefreshTokenRequest;
import com.musicspring.app.music_app.security.entity.CredentialEntity;
import com.musicspring.app.music_app.security.service.AuthService;
import com.musicspring.app.music_app.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }
    // we should implement userController authentication methods here.
    @PostMapping()
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        CredentialEntity user = authService.authenticate(authRequest);
        System.out.println(user);

        String token = jwtService.generateToken(user);
        System.out.println(token);

        return ResponseEntity.ok(new AuthResponse(token, user.getRefreshToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken (@RequestBody RefreshTokenRequest refreshTokenRequest){
        AuthResponse response = authService.refreshAccessToken(refreshTokenRequest.refreshToken());
        return ResponseEntity.ok(response);
    }
}