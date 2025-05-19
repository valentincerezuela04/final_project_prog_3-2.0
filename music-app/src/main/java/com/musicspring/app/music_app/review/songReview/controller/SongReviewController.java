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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/songreviews")
public class SongReviewController {

    private final SongReviewService songReviewService;

    @Autowired
    public SongReviewController(SongReviewService songReviewService, SongReviewMapper songReviewMapper) {
        this.songReviewService = songReviewService;
    }

    @GetMapping()
    public ResponseEntity<Page<SongReviewResponse>> getAllSongReviews(@RequestParam int size,@RequestParam int pageNumber, @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber,size, Sort.by(sort));
        return ResponseEntity.ok(songReviewService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongReviewResponse> getSongReviewById(@PathVariable Long id) {
            return ResponseEntity.ok(songReviewService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<SongReviewResponse> createSongReview (@RequestBody SongReviewRequest songReviewRequest){
        SongReviewResponse savedReview = songReviewService.createSong(songReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<SongReviewResponse>> getSongReviewsByUserId(
            @PathVariable Long userId,
            @RequestParam int size,
            @RequestParam int pageNumber,
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber,size,Sort.by(sort));
        Page<SongReviewResponse> songReviewResponsePage = songReviewService.findByUserId(userId,pageable);
        return ResponseEntity.ok(songReviewResponsePage);
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<Page<SongReviewResponse>> getSongReviewsBySongId(
            @PathVariable Long songId,
            @PathVariable int size,
            @RequestParam int pageNumber,
            @RequestParam String sort){
        Pageable pageable = PageRequest.of(pageNumber,size,Sort.by(sort));
        Page<SongReviewResponse> songReviewResponsePage = songReviewService.findBySongId(songId,pageable);
        return ResponseEntity.ok(songReviewResponsePage);
    }

}
