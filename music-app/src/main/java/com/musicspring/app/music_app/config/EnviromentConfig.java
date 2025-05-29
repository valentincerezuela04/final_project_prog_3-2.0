package com.musicspring.app.music_app.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EnviromentConfig {
    static {
        Dotenv dotenv = Dotenv.load();

        System.out.println(dotenv.get("JWT_SECRET"));
        System.setProperty("jwt.secret", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
        System.out.println(dotenv.get("SPOTIFY_CLIENT_ID"));
        System.setProperty("spotify.client.id", Objects.requireNonNull(dotenv.get("SPOTIFY_CLIENT_ID")));
        System.out.println(dotenv.get("SPOTIFY_CLIENT_SECRET"));
        System.setProperty("spotify.client.secret", dotenv.get("SPOTIFY_CLIENT_SECRET"));
        System.out.println(dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-id",
                Objects.requireNonNull(dotenv.get("GOOGLE_CLIENT_ID")));
        System.out.println(dotenv
                .get("GOOGLE_CLIENT_SECRET"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-secret",
                Objects.requireNonNull(dotenv.get("GOOGLE_CLIENT_SECRET")));
        System.out.println(dotenv.get("APP_OAUTH2_REDIRECT_URI"));
        System.setProperty("app.oauth2.redirect-uri", Objects.requireNonNull(
                dotenv.get("APP_OAUTH2_REDIRECT_URI")));

    }

}
