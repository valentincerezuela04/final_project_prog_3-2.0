package com.musicspring.app.music_app.review.songReview.model.entity;

import com.musicspring.app.music_app.review.ReviewEntity;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "song_reviews")
/// Constraint para el rating

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class SongReviewEntity extends ReviewEntity {

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    private SongEntity song;

}