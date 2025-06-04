package com.musicspring.app.music_app.controller;

import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.model.dto.*;
import com.musicspring.app.music_app.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.security.dto.AuthRequest;
import com.musicspring.app.music_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints related to user management and authentication")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user (simple signup)",
            description = "Creates a new user account with username and password only."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid registration data or username already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PostMapping("/auth/signup")
    /// changed signuprequest for authrequest
    public ResponseEntity<UserResponse> registerUser(
            @Parameter(description = "User registration data with username and password")
            @Valid @RequestBody AuthRequest authRequest) {
        UserResponse userResponse = userService.registerUser(authRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = "Register a new user with email",
            description = "Creates a new user account with username, email, and password. Returns authentication token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User registered successfully with authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthUserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid registration data, username or email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PostMapping("/auth/register")
    public ResponseEntity<AuthUserResponse> registerUserWithEmail(
            @Parameter(description = "User registration data with username, email, and password")
            @Valid @RequestBody SignupWithEmailRequest signupRequest) {
        AuthUserResponse authResponse = userService.registerUserWithEmail(signupRequest);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieves a list of all registered users in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Get a user by ID",
            description = "Retrieves a user using their internal unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(
            summary = "Get a user by username",
            description = "Retrieves a user using their unique username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @Parameter(description = "Username to search for", example = "johndoe123")
            @PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Operation(
            summary = "Delete a user by ID",
            description = "Permanently removes a user and their associated data from the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "User deleted successfully",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id) {
        userService.deleteUser(id);
    }

    @Operation(
            summary = "Update user information (full update)",
            description = "Updates user information including username, profile picture, and active status."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid update data or username already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "User update data")
            @Valid @RequestBody UserUpdateRequest updateRequest) {
        UserResponse updatedUser = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Partially update user information",
            description = "Updates only the provided user fields. Null fields are ignored."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid update data or username already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> partialUpdateUser(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Partial user update data")
            @Valid @RequestBody UserUpdateRequest updateRequest) {
        UserResponse updatedUser = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Update user password",
            description = "Changes the user's password after verifying the current password."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Password updated successfully",
                    content = @Content
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid password data, passwords don't match, or current password is incorrect",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Password update data with current and new passwords")
            @Valid @RequestBody PasswordUpdateRequest passwordRequest,
            @Parameter(hidden = true)
            Authentication authentication) {
        userService.updatePassword(id, passwordRequest, authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Update user profile",
            description = "Updates user profile information such as profile picture and biography."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Profile updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PatchMapping("/{id}/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @Parameter(description = "Internal user ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Profile update data")
            @Valid @RequestBody ProfileUpdateRequest profileRequest) {
        UserResponse updatedUser = userService.updateProfile(id, profileRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Get current authenticated user",
            description = "Retrieves the profile information of the currently authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Current user retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "401",
                    description = "User not authenticated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Authenticated user not found in database",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @Parameter(hidden = true)
            Authentication authentication) {
        UserResponse currentUser = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(currentUser);
    }
    @Operation(
            summary = "Search users by username",
            description = "Performs a paginated search on users by username pattern."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Search completed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid search parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @Parameter(description = "Search query for username", example = "john")
            @RequestParam String query,
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(userService.searchUsers(query, pageable));
    }

    @Operation(
            summary = "Get user profile",
            description = "Retrieves user profile information with review statistics."
    )
    @GetMapping("/username/{username}/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @Parameter(description = "Username", example = "johndoe123")
            @PathVariable String username) {
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    @Operation(
            summary = "Get user's album reviews",
            description = "Retrieves paginated album reviews written by the user."
    )
    @GetMapping("/username/{username}/album-reviews")
    public ResponseEntity<Page<AlbumReviewResponse>> getUserAlbumReviews(
            @Parameter(description = "Username", example = "johndoe123")
            @PathVariable String username,
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUserAlbumReviews(username, pageable));
    }

    @Operation(
            summary = "Get user's song reviews",
            description = "Retrieves paginated song reviews written by the user."
    )
    @GetMapping("/username/{username}/song-reviews")
    public ResponseEntity<Page<SongReviewResponse>> getUserSongReviews(
            @Parameter(description = "Username", example = "johndoe123")
            @PathVariable String username,
            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUserSongReviews(username, pageable));
    }
}