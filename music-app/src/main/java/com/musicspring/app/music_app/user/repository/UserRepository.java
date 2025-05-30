package com.musicspring.app.music_app.user.repository;

import com.musicspring.app.music_app.user.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
    // for username updates
    Boolean existsByUsernameAndUserIdNot(String username, Long userId);
    Page<UserEntity> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

    // jpql avg rating, better performance.
    @Query(value = """
    SELECT AVG(rating) FROM (
        SELECT rating FROM album_reviews ar WHERE ar.user_id = :userId AND ar.active = true
        UNION ALL
        SELECT rating FROM song_reviews sr WHERE sr.user_id = :userId AND sr.active = true
    ) all_reviews
    """, nativeQuery = true)
    Double calculateUserAverageRating(@Param("userId") Long userId);
}
