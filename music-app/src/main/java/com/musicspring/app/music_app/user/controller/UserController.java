package com.musicspring.app.music_app.user.controller;

import com.musicspring.app.music_app.user.model.dto.SignupRequest;
import com.musicspring.app.music_app.user.model.dto.SignupWithEmailRequest;
import com.musicspring.app.music_app.user.model.dto.UserResponse;
import com.musicspring.app.music_app.user.model.dto.AuthUserResponse;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.model.mapper.UserMapper;
import com.musicspring.app.music_app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {


    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = userService.registerUser(signupRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthUserResponse> registerUserWithEmail(@Valid @RequestBody SignupWithEmailRequest signupRequest) {
        AuthUserResponse authResponse = userService.registerUserWithEmail(signupRequest);
        return ResponseEntity.ok(authResponse);
    }


    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
