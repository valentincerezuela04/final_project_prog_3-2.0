package com.musicspring.app.music_app.user.repository;

import com.musicspring.app.music_app.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    // for username updates
    Boolean existsByUsernameAndUserIdNot(String username, Long userId);
}
