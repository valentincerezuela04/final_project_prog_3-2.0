package com.musicspring.app.music_app.user.service;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.entities.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.enums.Role;
import com.musicspring.app.music_app.security.repositories.CredentialRepository;
import com.musicspring.app.music_app.security.repositories.RoleRepository;
import com.musicspring.app.music_app.security.services.JwtService;
import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.SignupWithEmailRequest;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.dto.AuthUserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.CredentialMapper;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.repository.UserRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;
    private final CredentialRepository credentialRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, 
                       UserMapper userMapper, 
                       CredentialMapper credentialMapper,
                       CredentialRepository credentialRepository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
        this.credentialRepository = credentialRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional // throughourly recommended
    public UserResponse registerUser(SignupRequest signupRequest) {
        UserEntity user = userMapper.toUserEntity(signupRequest);

        CredentialEntity credential = credentialMapper.toCredentialEntity(signupRequest, user);
        credential.setRoles(Set.of(roleRepository
                .findByRole(Role.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Default role ROLE_USER not found"))));

        user.setCredential(credential);
        return userMapper.toResponse(user);
    }
    @Transactional
    public AuthUserResponse registerUserWithEmail(SignupWithEmailRequest signupRequest) {

        if (credentialRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists with email: " + signupRequest.getEmail());
        }

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new IllegalArgumentException("Username already taken: " + signupRequest.getUsername());
        }

        UserEntity user = userMapper.toUserEntity(signupRequest);

        user = userRepository.save(user);

        CredentialEntity credential = credentialMapper.toCredentialEntity(signupRequest, user);

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        credential.setRoles(Set.of(roleRepository
                .findByRole(Role.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Default role ROLE_USER not found."))));

        credential = credentialRepository.save(credential);

        String token = jwtService.generateToken(credential);

        return AuthUserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(credential.getEmail())
                .token(token)
                .build();
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }


    public UserResponse findById(Long id){
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("User with ID: " + id + " was not found."));
    }

    public UserEntity findEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User with ID: " + id + " was not found."));
    }

    public UserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(()-> new EntityNotFoundException("User with Username: " + username + " was not found."));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
}