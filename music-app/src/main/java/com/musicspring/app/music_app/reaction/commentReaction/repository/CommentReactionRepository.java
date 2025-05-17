//package com.musicspring.app.music_app.reaction.commentReaction.repository;
//
//import com.musicspring.app.music_app.reaction.commentReaction.model.entity.CommentReactionEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CommentReactionRepository extends JpaRepository<CommentReactionEntity, Long> {
//
//    /// Finds all comment reactions for a specific comment by its ID
//    List<CommentReactionEntity> findByComment_CommentId(Long commentId);
//    /// Finds all comment reactions made by a specific user by user ID
//    List<CommentReactionEntity> findByUser_UserId(Long userId);
//
//}
