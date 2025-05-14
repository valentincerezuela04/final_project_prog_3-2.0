package com.musicspring.app.music_app.review.songReview.controller;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.review.songReview.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.review.songReview.service.SongReviewService;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/songreviews")
public class SongReviewController {

    @Autowired
    private SongReviewService songReviewService;
    @Autowired
    private SongReviewMapper songReviewMapper;

    @GetMapping()
    public ResponseEntity<List<SongReviewResponse>> getAllSongReviews(Pageable pageable) {
        Page<SongReviewEntity> songReviewPage = songReviewService.findAll(pageable);
        return ResponseEntity.ok(songReviewMapper.toResponseList(songReviewPage.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongReviewResponse> getSongReviewById(@PathVariable Long id) {
        try {
            SongReviewEntity songReviewEntity = songReviewService.findById(id);
            return ResponseEntity.ok(songReviewMapper.toResponse(songReviewEntity));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); ///Reemplazar por una excepcion personalizada y luego que lo maneje el ControllerAdvice
        }
    }

    @PostMapping()
    public ResponseEntity<SongReviewResponse> createSongReview (@RequestBody SongReviewRequest songReviewRequest){

    }
}
