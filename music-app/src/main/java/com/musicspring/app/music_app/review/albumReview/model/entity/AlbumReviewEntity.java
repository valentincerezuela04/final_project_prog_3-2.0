package com.musicspring.app.music_app.review.albumReview.model.entity;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.review.Review;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "album_reviews")
@Check(constraints = "rating >= 0.5 AND rating <= 5.0")
/// Constraint para el rating

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class AlbumReviewEntity extends Review {
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

}