package com.musicspring.app.music_app.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NotBlank
public record AuthRequest(
        @Size(min = 3, max = 50) String username,
        @Size(min = 8, max = 120) String password) {}
