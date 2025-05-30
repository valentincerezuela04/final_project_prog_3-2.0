package com.musicspring.app.music_app.user.service;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import com.musicspring.app.music_app.security.entities.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.enums.Role;
import com.musicspring.app.music_app.security.repositories.CredentialRepository;
import com.musicspring.app.music_app.security.repositories.RoleRepository;
import com.musicspring.app.music_app.security.services.JwtService;
import com.musicspring.app.music_app.user.model.dto.*;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.CredentialMapper;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (credentialRepository.findByEmail(signupRequest.getEmail()).isPresent())
            throw new IllegalArgumentException("User already exists with email: " + signupRequest.getEmail());

        if (userRepository.existsByUsername(signupRequest.getUsername()))
            throw new IllegalArgumentException("Username already taken: " + signupRequest.getUsername());

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


    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " was not found."));
    }

    public UserEntity findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " was not found."));
    }

    public UserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User with Username: " + username + " was not found."));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest updateRequest) {

        UserEntity existingUser = findEntityById(id);
        // Update only the present fields
        if (updateRequest.getUsername() != null &&
                !updateRequest.getUsername().equals(existingUser.getUsername())) {

            if (userRepository.existsByUsernameAndUserIdNot(updateRequest.getUsername(), id))
                throw new IllegalArgumentException("Username already taken: " + updateRequest.getUsername());
            existingUser.setUsername(updateRequest.getUsername());
        }

        CredentialEntity credential = existingUser.getCredential();

        if (updateRequest.getProfilePictureUrl() != null)
            credential.setProfilePictureUrl(updateRequest.getProfilePictureUrl());

        if (updateRequest.getActive() != null)
            existingUser.setActive(updateRequest.getActive());

        UserEntity savedUser = userRepository.save(existingUser);
        return userMapper.toResponse(savedUser);
    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateRequest passwordRequest, Authentication authentication) {
        UserEntity user = findEntityById(id);
        CredentialEntity credential = user.getCredential();

        if (credential == null)
            throw new IllegalStateException("User has no credentials");


        // Verify current password
        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), credential.getPassword()))
            throw new IllegalArgumentException("Current password is incorrect");

        if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword()))
            throw new IllegalArgumentException("New password and confirmation do not match");

        credential.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        credentialRepository.save(credential);
    }

    @Transactional
    public UserResponse updateProfile(Long id, ProfileUpdateRequest profileRequest) {
        UserEntity user = findEntityById(id);
        CredentialEntity credential = user.getCredential();

        if (credential != null) {
            if (profileRequest.getProfilePictureUrl() != null) {
                credential.setProfilePictureUrl(profileRequest.getProfilePictureUrl());
            }

            if (profileRequest.getBiography() != null) {
                credential.setBiography(profileRequest.getBiography());
            }

            credentialRepository.save(credential);
        }

        return userMapper.toResponse(user);
    }

    public UserResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        CredentialEntity credential = credentialRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userMapper.toResponse(credential.getUser());
    }

}