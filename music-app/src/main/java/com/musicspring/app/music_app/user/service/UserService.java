package com.musicspring.app.music_app.user.service;

import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.entity.CredentialEntity;
import com.musicspring.app.music_app.user.model.entity.ERole;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserEntity registerUser(SignupRequest signupRequest) {
        UserEntity user = UserEntity.builder()
                .username(signupRequest.getUsername())
                .role(ERole.USER)
                .build();

        CredentialEntity credential = CredentialEntity.builder()
                .password(signupRequest.getPassword())
                .user(user)
                .build();

        user.setCredential(credential);
        return userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse);
    }

    /// Agregado por manu. Es posible que cambie
    public UserEntity findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User with ID: " + id + " was not found."));
    }

    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById (Long id){
        return userRepository.existsById(id);
    }
}