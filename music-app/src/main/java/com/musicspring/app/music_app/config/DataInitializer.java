package com.musicspring.app.music_app.config;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.repositories.CredentialRepository;
import com.musicspring.app.music_app.song.repository.SongRepository;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/* this class implements a spring boot functional interface, and string... args is part of it.
initializes data, used for testing w/ h2.
 */
@Component

public class DataInitializer {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final SongRepository songRepository;
    private final PasswordEncoder PasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, CredentialRepository credentialRepository, SongRepository songRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.songRepository = songRepository;
        PasswordEncoder = passwordEncoder;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void run() {
        if (userRepository.count() == 0) {
            // Create admin user


            CredentialEntity adminCredential = CredentialEntity.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .build();
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .credential(adminCredential)
                    .active(true)
                    .build();

            CredentialEntity userCredential = CredentialEntity.builder()
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("password") )
                    .build();
            admin = userRepository.save(admin);

            UserEntity user = UserEntity.builder()
                    .username("user")
                    .credential(userCredential)
                    .active(true)
                    .build();
            user = userRepository.save(user);

            System.out.println("Test users created:");
            System.out.println("- admin/admin123 (ID: " + admin.getUserId() + ")");
            System.out.println("- user/password (ID: " + user.getUserId() + ")");
        }

    }
}