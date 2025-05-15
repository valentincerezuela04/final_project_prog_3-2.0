package com.musicspring.app.music_app.review.albumReview.controller;

import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.review.albumReview.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.review.albumReview.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.review.albumReview.model.mapper.AlbumReviewMapper;
import com.musicspring.app.music_app.review.albumReview.service.AlbumReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/albumreviews")
public class AlbumReviewController {
    private final AlbumReviewService albumReviewService;
    private final AlbumReviewMapper albumReviewMapper;

    @Autowired
    public AlbumReviewController(AlbumReviewService albumReviewService, AlbumReviewMapper albumReviewMapper) {
        this.albumReviewService = albumReviewService;
        this.albumReviewMapper = albumReviewMapper;
    }

    @GetMapping()
    public ResponseEntity<List<AlbumReviewResponse>> getAllAlbumReviews(Pageable pageable) {
        Page<AlbumReviewEntity> albumReviewPage = albumReviewService.findAll(pageable);
        return ResponseEntity.ok(albumReviewMapper.toResponseList(albumReviewPage.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumReviewResponse> getAlbumReviewById(@PathVariable Long id) {
        try {
            AlbumReviewEntity albumReviewEntity = albumReviewService.findById(id);
            return ResponseEntity.ok(albumReviewMapper.toResponse(albumReviewEntity));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build(); ///Reemplazar por una excepción personalizada y luego que lo maneje el ControllerAdvice
        }
    }

    @PostMapping()
    public ResponseEntity<AlbumReviewResponse> createAlbumReview(@RequestBody AlbumReviewRequest albumReviewRequest) {
        AlbumReviewEntity savedReview = albumReviewService.save(albumReviewRequest);

        // Convertir la entidad guardada a DTO de respuesta
        AlbumReviewResponse response = albumReviewMapper.toResponse(savedReview);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AlbumReviewResponse>> getAlbumReviewsByUserId(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<AlbumReviewEntity> albumReviewPage = albumReviewService.findByUserId(userId, pageable);
        Page<AlbumReviewResponse> responsePage = albumReviewMapper.toResponsePage(albumReviewPage);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<Page<AlbumReviewResponse>> getAlbumReviewsByAlbumId(
            @PathVariable Long albumId,
            Pageable pageable) {
        Page<AlbumReviewEntity> albumReviewEntityPage = albumReviewService.findByAlbumId(albumId, pageable);
        Page<AlbumReviewResponse> albumReviewResponsePage = albumReviewMapper.toResponsePage(albumReviewEntityPage);
        return ResponseEntity.ok(albumReviewResponsePage);
    }
}
