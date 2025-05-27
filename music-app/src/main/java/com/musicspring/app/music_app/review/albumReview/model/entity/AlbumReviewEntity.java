package com.musicspring.app.music_app.review.albumReview.model.entity;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.review.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "album_reviews")
/// Constraint para el rating

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class AlbumReviewEntity extends ReviewEntity {
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

}