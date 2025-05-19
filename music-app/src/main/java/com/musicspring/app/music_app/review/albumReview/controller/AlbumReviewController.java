package com.musicspring.app.music_app.review.albumReview.controller;

import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.service.AlbumReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/albumreviews")
public class AlbumReviewController {
    private final AlbumReviewService albumReviewService;

    @Autowired
    public AlbumReviewController(AlbumReviewService albumReviewService, AlbumReviewMapper albumReviewMapper) {
        this.albumReviewService = albumReviewService;
    }

    @GetMapping
    public ResponseEntity<Page<AlbumReviewResponse>> getAllAlbumReviews(
            @RequestParam int size,
            @RequestParam int pageNumber,
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        return ResponseEntity.ok(albumReviewService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumReviewResponse> getAlbumReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(albumReviewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AlbumReviewResponse> createAlbumReview(@RequestBody AlbumReviewRequest albumReviewRequest) {
        AlbumReviewResponse savedReview = albumReviewService.createAlbum(albumReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AlbumReviewResponse>> getAlbumReviewsByUserId(
            @PathVariable Long userId,
            @RequestParam int size,
            @RequestParam int pageNumber,
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<AlbumReviewResponse> albumReviewResponsePage = albumReviewService.findByUserId(userId, pageable);
        return ResponseEntity.ok(albumReviewResponsePage);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<Page<AlbumReviewResponse>> getAlbumReviewsByAlbumId(
            @PathVariable Long albumId,
            @RequestParam int size,
            @RequestParam int pageNumber,
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<AlbumReviewResponse> albumReviewResponsePage = albumReviewService.findByAlbumId(albumId, pageable);
        return ResponseEntity.ok(albumReviewResponsePage);
    }
}

