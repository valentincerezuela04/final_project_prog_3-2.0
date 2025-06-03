package com.musicspring.app.music_app.repository;

import com.musicspring.app.music_app.model.entity.ReactionEntity;
import com.musicspring.app.music_app.model.enums.ReactedType;
import com.musicspring.app.music_app.model.enums.ReactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

    @Query("SELECT r FROM ReactionEntity r WHERE (:reaction IS NULL OR :reaction = r.reactionType) AND (:reacted IS NULL OR :reacted = r.reactedType")
    Page<ReactionEntity> findByReactionTypeAndReactedType(@Param("reaction") ReactionType reactionType,
                                                          @Param("reacted")ReactedType reactedType,
                                                          Pageable pageable);

}
