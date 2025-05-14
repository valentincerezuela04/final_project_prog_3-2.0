package com.musicspring.app.music_app.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}