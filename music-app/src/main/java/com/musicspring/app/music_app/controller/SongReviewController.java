package com.musicspring.app.music_app.controller;

import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.model.mapper.SongReviewMapper;
import com.musicspring.app.music_app.service.SongReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/songreviews")
public class SongReviewController {

    private final SongReviewService songReviewService;

    @Autowired
    public SongReviewController(SongReviewService songReviewService, SongReviewMapper songReviewMapper) {
        this.songReviewService = songReviewService;
    }

    @Operation(
            summary = "Retrieve all song reviews",
            description = "Fetches a paginated list of all song reviews, sorted by the specified parameter.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reviews retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Entity not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request, invalid parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping()
    public ResponseEntity<Page<SongReviewResponse>> getAllSongReviews(
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "date")
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber,size, Sort.by(sort));
        return ResponseEntity.ok(songReviewService.findAll(pageable));
    }

    @Operation(
            summary = "Retrieve a song review by ID",
            description = "Fetches the details of a single song review identified by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Song review retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongReviewResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Song review not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID supplied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<SongReviewResponse> getSongReviewById(
            @Parameter(description = "ID of the song review to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(songReviewService.findById(id));
    }

    @Operation(
            summary = "Create a new song review",
            description = "Creates a new song review with the provided data."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Song review created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongReviewResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @PostMapping()
    public ResponseEntity<SongReviewResponse> createSongReview(
            @Parameter(description = "Data for the new song review", required = true)
            @Valid @RequestBody SongReviewRequest songReviewRequest) {
        SongReviewResponse savedReview = songReviewService.createSongReview(songReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @Operation(
            summary = "Get song reviews by user ID",
            description = "Retrieves a paginated list of song reviews created by the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Song reviews retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<SongReviewResponse>> getSongReviewsByUserId(
            @Parameter(description = "ID of the user whose song reviews are requested", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "date")
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<SongReviewResponse> songReviewResponsePage = songReviewService.findByUserId(userId, pageable);
        return ResponseEntity.ok(songReviewResponsePage);
    }

    @Operation(
            summary = "Get song reviews by song ID",
            description = "Retrieves a paginated list of reviews for the specified song."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Song reviews retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Song not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/song/{songId}")
    public ResponseEntity<Page<SongReviewResponse>> getSongReviewsBySongId(
            @Parameter(description = "ID of the song whose reviews are requested", example = "1")
            @PathVariable Long songId,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "date")
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<SongReviewResponse> songReviewResponsePage = songReviewService.findBySongId(songId, pageable);
        return ResponseEntity.ok(songReviewResponsePage);
    }

    @Operation(
            summary = "Get song reviews by Spotify ID",
            description = "Retrieves a paginated list of reviews for the specified song using its Spotify ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Song reviews retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Song not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<Page<SongReviewResponse>> getSongReviewsBySpotifyId(
            @Parameter(description = "Spotify ID of the song whose reviews are requested", example = "4iV5W9uYEdYUVa79Axb7Rh")
            @PathVariable String spotifyId,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "date")
            @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<SongReviewResponse> songReviewResponsePage = songReviewService.findBySpotifyId(spotifyId, pageable);
        return ResponseEntity.ok(songReviewResponsePage);
    }

}
