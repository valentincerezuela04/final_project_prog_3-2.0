package com.musicspring.app.music_app.config;

import com.musicspring.app.music_app.security.entity.CredentialEntity;
import com.musicspring.app.music_app.security.entity.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.enums.Role;
import com.musicspring.app.music_app.security.repository.CredentialRepository;
import com.musicspring.app.music_app.security.repository.RoleRepository;
import com.musicspring.app.music_app.repository.SongRepository;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/* this class implements a spring boot functional interface, and string... args is part of it.
initializes data, used for testing w/ h2.
 */
@Component

public class DataInitializer {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;
    private final SongRepository songRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, 
                          CredentialRepository credentialRepository,
                          RoleRepository roleRepository,
                          SongRepository songRepository, 
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.roleRepository = roleRepository;
        this.songRepository = songRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void run() {
        RoleEntity userRole = roleRepository.findByRole(Role.ROLE_USER)
                .orElseGet(() -> roleRepository.save(RoleEntity.builder().role(Role.ROLE_USER).build()));
        
        RoleEntity adminRole = roleRepository.findByRole(Role.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(RoleEntity.builder().role(Role.ROLE_ADMIN).build()));

        if (userRepository.count() == 0) {
            UserEntity admin = UserEntity.builder()
                    .username("admin")
                    .active(true)
                    .build();
            admin = userRepository.save(admin);

            Set<RoleEntity> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            CredentialEntity adminCredential = CredentialEntity.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin123"))
                    .provider(AuthProvider.LOCAL)
                    .user(admin)
                    .refreshToken("1")
                    .roles(adminRoles)
                    .build();
            credentialRepository.save(adminCredential);

            UserEntity user = UserEntity.builder()
                    .username("user")
                    .active(true)
                    .build();
            user = userRepository.save(user);

            Set<RoleEntity> userRoles = new HashSet<>();
            userRoles.add(userRole);
            
            CredentialEntity userCredential = CredentialEntity.builder()
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("password"))
                    .provider(AuthProvider.LOCAL)
                    .user(user)
                    .refreshToken("2")
                    .roles(userRoles)
                    .build();
            credentialRepository.save(userCredential);

            System.out.println("Test users created:");
            System.out.println("- admin/admin123 (ID: " + admin.getUserId() + ")");
            System.out.println("- user/password (ID: " + user.getUserId() + ")");
        }
    }
}