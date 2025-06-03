package com.musicspring.app.music_app.model.dto.request;

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