//package com.musicspring.app.music_app.reaction.commentReaction.service;
//
//import com.musicspring.app.music_app.shared.IService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CommentReactionService implements IService<CommentReactionEntity> {
//
//    private final CommentReactionRepository commentReactionRepository;
//
//    @Autowired
//    public CommentReactionService(CommentReactionRepository commentReactionRepository){
//        this.commentReactionRepository = commentReactionRepository;
//    }
//
//    @Override
//    public Page<CommentReactionEntity> findAll(Pageable pageable) {
//        return commentReactionRepository.findAll(pageable);
//    }
//
//    @Override
//    public CommentReactionEntity findById(Long id) {
//        return commentReactionRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Comment reaction with ID: " + id + " was not found."));
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        commentReactionRepository.deleteById(id);
//    }
//
//    @Override
//    public CommentReactionEntity save(CommentReactionEntity commentReactionEntity) {
//        return commentReactionRepository.save(commentReactionEntity);
//    }
//}
