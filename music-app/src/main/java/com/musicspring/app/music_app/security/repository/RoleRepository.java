package com.musicspring.app.music_app.security.repository;

import com.musicspring.app.music_app.security.entity.RoleEntity;
import com.musicspring.app.music_app.security.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole(Role role);
}