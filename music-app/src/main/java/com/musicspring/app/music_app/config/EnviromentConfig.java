package com.musicspring.app.music_app.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnviromentConfig {
    static {
        try {
            Dotenv dotenv = Dotenv.load();
            
            // Validate and set required environment variables
            setSystemProperty("jwt.secret", dotenv.get("JWT_SECRET"), "JWT_SECRET");
            setSystemProperty("spotify.client.id", dotenv.get("SPOTIFY_CLIENT_ID"), "SPOTIFY_CLIENT_ID");
            setSystemProperty("spotify.client.secret", dotenv.get("SPOTIFY_CLIENT_SECRET"), "SPOTIFY_CLIENT_SECRET");
            setSystemProperty("spring.security.oauth2.client.registration.google.client-id", dotenv.get("GOOGLE_CLIENT_ID"), "GOOGLE_CLIENT_ID");
            setSystemProperty("spring.security.oauth2.client.registration.google.client-secret", dotenv.get("GOOGLE_CLIENT_SECRET"), "GOOGLE_CLIENT_SECRET");
            setSystemProperty("app.oauth2.redirect-uri", dotenv.get("APP_OAUTH2_REDIRECT_URI"), "APP_OAUTH2_REDIRECT_URI");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment variables from .env file. " +
                    "Please ensure .env file exists in the project root with all required variables: " +
                    "JWT_SECRET, SPOTIFY_CLIENT_ID, SPOTIFY_CLIENT_SECRET, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, APP_OAUTH2_REDIRECT_URI", e);
        }
    }
    
    private static void setSystemProperty(String propertyName, String value, String envVarName) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Environment variable " + envVarName + " is required but not found in .env file");
        }
        System.setProperty(propertyName, value);
    }
}
