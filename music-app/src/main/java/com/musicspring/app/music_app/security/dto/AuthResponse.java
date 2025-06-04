package com.musicspring.app.music_app.security.dto;

public record AuthResponse(String token,
                           String refreshToken) {
}
