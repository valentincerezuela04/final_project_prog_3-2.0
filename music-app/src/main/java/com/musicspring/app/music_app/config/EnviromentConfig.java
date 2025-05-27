package com.musicspring.app.music_app.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnviromentConfig {
    static {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
        System.setProperty("spotify.client.id", dotenv.get("SPOTIFY_CLIENT_ID"));
        System.setProperty("spotify.client.secret", dotenv.get("SPOTIFY_CLIENT_SECRET"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-id", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-secret", dotenv.get("GOOGLE_CLIENT_SECRET"));
    }
}
