package com.musicspring.app.music_app.reaction.reviewReaction.model.entity;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionEntity;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "review_reactions")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReviewReactionEntity extends ReactionEntity {

    @ManyToOne
    @JoinColumn(name = "album_review_id")
    private AlbumReviewEntity albumReview;

    @ManyToOne
    @JoinColumn(name = "song_review_id")
    private SongReviewEntity songReview;

    /**
     * Validates that only one of the two fields (albumReview or songReview) is set.
     * Throws IllegalStateException if both are null or both are set.
     */
    public void validate() {
        boolean hasAlbumReview = this.albumReview != null;
        boolean hasSongReview = this.songReview != null;

        if (hasAlbumReview == hasSongReview) { /// Both true or both false
            throw new IllegalStateException("Exactly one of albumReview or songReview must be set.");
        }
    }

    /// Returns the ID of the associated review (album or song), or null if none is set.
    public Long getAssociatedReviewId(){
        if(albumReview != null){
            return albumReview.getAlbumReviewId();
        } else if (songReview != null) {
            return songReview.getSongReviewId();
        }
        return null;
    }
}
