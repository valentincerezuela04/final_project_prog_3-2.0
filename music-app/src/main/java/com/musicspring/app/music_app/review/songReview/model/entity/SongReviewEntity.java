package com.musicspring.app.music_app.review.songReview.model.entity;

import com.musicspring.app.music_app.review.Review;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "song_reviews")
@Check(constraints = "rating >= 0.5 AND rating <= 5.0")
/// Constraint para el rating

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class SongReviewEntity extends Review {

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

}