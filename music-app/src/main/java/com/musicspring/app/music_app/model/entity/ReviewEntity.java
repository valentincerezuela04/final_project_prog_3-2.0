package com.musicspring.app.music_app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "reviews")
@Check(constraints = "rating >= 0.5 AND rating <= 5.0")

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public abstract class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

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
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
