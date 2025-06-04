package com.musicspring.app.music_app.security.dto;
/// Add AuthUserResponse atributtes
public record AuthResponse(String token,
                           String refreshToken) {
}
