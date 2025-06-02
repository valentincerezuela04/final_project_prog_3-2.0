package com.musicspring.app.music_app.model.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthUserResponse {
    private Long id;
    private String username;
    private String email;
    private String token;
}