package com.musicspring.app.music_app.review.albumReview.model.entity;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "album_reviews")
@Check(constraints = "rating >= 0.5 AND rating <= 5.0")
/// Constraint para el rating

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AlbumReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_review_id")
    private Long albumReviewId;

    @Column(name = "rating", columnDefinition = "DECIMAL(3,2) DEFAULT 5.0")
    private Double rating;

    @Column(name = "description",length = 500)
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}