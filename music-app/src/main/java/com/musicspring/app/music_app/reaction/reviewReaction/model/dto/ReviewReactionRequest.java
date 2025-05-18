package com.musicspring.app.music_app.reaction.reviewReaction.model.dto;

import com.musicspring.app.music_app.reaction.common.model.entity.ReactionType;
import com.musicspring.app.music_app.reaction.reviewReaction.model.entity.ReviewReactionEntity;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor

public class ReviewReactionRequest {

    /// Fields are declared as final to make the DTO immutable,
    /// ensuring thread-safety and preventing accidental modifications after creation.
    @NotNull(message = "Reaction type must not be null")
    private final ReactionType reactionType;

    private final Long albumReviewId;
    private final Long songReviewId;

    /// Same behavior to validate() method on ReviewReactionEntity: validates
    public void validate() {
        boolean hasAlbumReview = albumReviewId != null;
        boolean hasSongReview = songReviewId != null;

        if (hasAlbumReview == hasSongReview) {
            throw new IllegalStateException("Exactly one of albumReview or songReview must be set.");
        }
    }

    public ReviewReactionEntity toEntity(UserEntity userEntity,
                                         AlbumReviewEntity albumReviewEntity,
                                         SongReviewEntity songReviewEntity){

        /// We create a new instance of ReviewReactionEntity manually using the default constructor.
        /// Lombok's builder does NOT include fields inherited from the superclass (ReactionEntity),
        /// such as reactionType and user, so using the builder would omit those important fields.
        ReviewReactionEntity entity = new ReviewReactionEntity();

        entity.setReactionType(this.reactionType);
        entity.setUser(userEntity);

        /// Set the appropriate review association.
        /// Exactly one of albumReview or songReview must be non-null.
        /// We check and set only the non-null one.
        if(albumReviewEntity != null && songReviewEntity == null){
            entity.setAlbumReview(albumReviewEntity);
        } else if (songReviewEntity != null && albumReviewEntity == null){
            entity.setSongReview(songReviewEntity);
        } else {
            throw new IllegalStateException("Exactly one of albumReview or songReview must be set.");
        }

        /// Validate entity state according to business rules.
        entity.validate();

        return entity;
    }
}
