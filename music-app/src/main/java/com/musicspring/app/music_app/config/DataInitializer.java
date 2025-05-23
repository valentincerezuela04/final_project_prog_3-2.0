package com.musicspring.app.music_app.config;

import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import com.musicspring.app.music_app.user.model.entity.CredentialEntity;
import com.musicspring.app.music_app.user.model.entity.ERole;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.repository.UserRepository;
import com.musicspring.app.music_app.user.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/* this class implements a spring boot functional interface, and string... args is part of it.
initializes data, used for testing w/ h2.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final SongRepository songRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository, CredentialRepository credentialRepository, SongRepository songRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.songRepository = songRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create admin user
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .role(ERole.ADMIN)
                    .active(true)
                    .build();
            admin = userRepository.save(admin);

            CredentialEntity adminCredential = CredentialEntity.builder()
                    .id(admin.getUserId())
                    .user(admin)
                    .password("admin123")
                    .build();
            credentialRepository.save(adminCredential);

            UserEntity user = UserEntity.builder()
                    .username("user")
                    .role(ERole.USER)
                    .active(true)
                    .build();
            user = userRepository.save(user);

            CredentialEntity userCredential = CredentialEntity.builder()
                    .id(user.getUserId())
                    .user(user)
                    .password("password")
                    .build();
            credentialRepository.save(userCredential);


            System.out.println("Test users created:");
            System.out.println("- admin/admin123 (ID: " + admin.getUserId() + ")");
            System.out.println("- user/password (ID: " + user.getUserId() + ")");
        }

    }
}