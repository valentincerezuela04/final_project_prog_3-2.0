package com.musicspring.app.music_app.security.oauth2.services;

import com.musicspring.app.music_app.security.entity.CredentialEntity;
import com.musicspring.app.music_app.security.entity.RoleEntity;
import com.musicspring.app.music_app.security.enums.AuthProvider;
import com.musicspring.app.music_app.security.enums.Role;
import com.musicspring.app.music_app.security.oauth2.dto.CustomOAuth2User;
import com.musicspring.app.music_app.security.repository.CredentialRepository;
import com.musicspring.app.music_app.security.repository.RoleRepository;
import com.musicspring.app.music_app.model.entity.UserEntity;
import com.musicspring.app.music_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * CustomOAuth2UserService handles OAuth2 user information processing.
 * This service extends Spring's DefaultOAuth2UserService to customize how OAuth2 user
 * information is processed and stored in the application database. It is responsible for:
 * 1. Loading user information from OAuth2 providers (Google, etc.)
 * 2. Mapping OAuth2 user attributes to application user entities
 * 3. Creating or updating user records based on OAuth2 data
 * 4. Assigning appropriate roles and permissions to OAuth2 users
 * 5. Maintaining provider-specific information (provider ID, profile pictures, etc.)
 * The service ensures that OAuth2 users are properly integrated into the application's
 * user management and security system.
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * @param credentialRepository Repository for managing user credentials
     * @param userRepository       Repository for managing user entities
     * @param roleRepository       Repository for managing user roles
     */
    @Autowired
    public CustomOAuth2UserService(CredentialRepository credentialRepository,
                                   UserRepository userRepository,
                                   RoleRepository roleRepository) {
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Loads and processes OAuth2 user information.
     * This method is called by Spring Security during the OAuth2 authentication flow.
     * It delegates to the parent class to load basic OAuth2 user information, then
     * processes that information to create or update application user records.
     * @param userRequest OAuth2UserRequest containing access token and client registration
     * @return CustomOAuth2User containing both OAuth2 attributes and application user data
     * @throws OAuth2AuthenticationException if user processing fails
     */
    @Override
    @Transactional  // Ensures database operations are atomic
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Load basic OAuth2 user information from provider
        OAuth2User oauth2User = super.loadUser(userRequest);

        // Process the OAuth2 user data and create/update application user
        return processOAuth2User(oauth2User);
    }

    /**
     * Processes OAuth2 user data and manages application user records.
     * This method extracts user information from OAuth2 provider attributes,
     * then creates or updates the corresponding application user records.
     * It handles both new user registration and existing user updates.
     * @param oauth2User OAuth2User containing provider attributes
     * @return CustomOAuth2User wrapping both OAuth2 data and application credentials
     */
    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        // Extract user information from OAuth2 provider attributes
        String email = oauth2User.getAttribute("email");           // Email address (required)
        String googleId = oauth2User.getAttribute("sub");          // Google's unique user identifier
        String name = oauth2User.getAttribute("name");             // Display name
        String pictureUrl = oauth2User.getAttribute("picture");    // Profile picture URL

        // Find existing user credential by email
        Optional<CredentialEntity> credentialOptional = credentialRepository.findByEmail(email);
        CredentialEntity credential;

        if (credentialOptional.isPresent()) {
            // Update existing user with latest OAuth2 information
            credential = credentialOptional.get();
            credential.setProvider(AuthProvider.GOOGLE);       // Ensure provider is set correctly
            credential.setProviderId(googleId);                // Update provider ID
            credential.setProfilePictureUrl(pictureUrl);       // Update profile picture
        } else {
            // Create new user account from OAuth2 data
            credential = createNewOAuth2User(email, googleId, name, pictureUrl);
        }

        // Save updated credential information
        credentialRepository.save(credential);

        // Return custom OAuth2 user containing both provider data and application credentials
        return new CustomOAuth2User(oauth2User.getAttributes(), credential);
    }

    /**
     * Creates a new OAuth2 user account.
     * This method creates both UserEntity and CredentialEntity records for a new OAuth2 user.
     * It assigns default roles and sets up the user with appropriate permissions.
     * @param email      User's email address from OAuth2 provider
     * @param googleId   Google's unique identifier for the user
     * @param name       User's display name from OAuth2 provider
     * @param pictureUrl URL to user's profile picture
     * @return CredentialEntity for the newly created user
     */
    private CredentialEntity createNewOAuth2User(String email, String googleId, String name, String pictureUrl) {
        // Create main user entity with basic information
        UserEntity user = UserEntity.builder()
                .username(generateUsernameFromEmail(email))  // Generate unique username
                .active(true)                                // New OAuth2 users are active by default
                .build();

        // Save user entity to generate database ID
        user = userRepository.save(user);

        // Create credential entity with OAuth2 provider information and proper roles
        return CredentialEntity.builder()
                .email(email)                        // Store email for identification
                .provider(AuthProvider.GOOGLE)       // Set OAuth2 provider
                .providerId(googleId)                // Store provider's unique user ID
                .profilePictureUrl(pictureUrl)       // Store profile picture URL
                .user(user)                          // Link to user entity
                .roles(getDefaultRoles())
                .build();
    }

    /**
     * Generates a unique username from email address.
     * This method creates a unique username by combining the email prefix with a timestamp.
     * This ensures uniqueness even if multiple users have similar email prefixes.
     * Example: "john.doe@gmail.com" becomes "john.doe_1640995200000"
     * @param email User's email address
     * @return Unique username string
     */
    private String generateUsernameFromEmail(String email) {
        return email.split("@")[0] + "_" + System.currentTimeMillis();
    }

    /**
     * Retrieves default roles for new OAuth2 users.
     * FIXED: Added this method to properly assign USER role to new OAuth2 users.
     * Previously, OAuth2 users were created with empty role sets, causing authorization issues.
     * @return Set of RoleEntity objects representing default user permissions
     */
    private Set<RoleEntity> getDefaultRoles() {
        Set<RoleEntity> roles = new HashSet<>();

        // Find and assign the standard USER role
        // This provides basic application access permissions
        roleRepository.findByRole(Role.ROLE_USER)
                .ifPresent(roles::add);

        return roles;
    }
}
