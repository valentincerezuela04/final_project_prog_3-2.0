package com.musicspring.app.music_app.user.repository;

import com.musicspring.app.music_app.user.model.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
}