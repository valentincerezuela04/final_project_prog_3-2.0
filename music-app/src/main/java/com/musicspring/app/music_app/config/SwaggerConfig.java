package com.musicspring.app.music_app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MusicApp API Documentation")
                        .version("1.0")
                        .description("API documentation for the MusicApp project, including endpoints for managing song reviews, users, and albums."));
    }
}