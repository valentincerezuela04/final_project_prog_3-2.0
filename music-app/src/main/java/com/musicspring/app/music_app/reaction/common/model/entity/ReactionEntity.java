package com.musicspring.app.music_app.reaction.common.model.entity;

import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ReactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id")
    private Long reactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType type;

    /// FetchType.LAZY prevents unnecessary loading of the user when it's not always needed.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /// updatable = false ensures the field cannot be modified after creation.
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Callback that is automatically executed before inserting the entity into the database.
     * It is used to set the creation date and time of the reaction.
     * This ensures that the 'createdAt' field has a consistent and automatic value without manual intervention.
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
