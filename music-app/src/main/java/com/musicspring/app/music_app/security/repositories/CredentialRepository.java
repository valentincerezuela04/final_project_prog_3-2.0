package com.musicspring.app.music_app.security.repositories;

import com.musicspring.app.music_app.security.entities.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
    Optional<CredentialEntity> findByEmail(String email);
}
