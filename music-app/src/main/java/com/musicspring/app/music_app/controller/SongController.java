package com.musicspring.app.music_app.controller;

import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.model.dto.request.SongRequest;
import com.musicspring.app.music_app.model.dto.response.SongResponse;
import com.musicspring.app.music_app.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/songs")
@Tag(name = "Songs", description = "Endpoints related to song management")
public class SongController {

    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Operation(
            summary = "Get all songs (paginated)",
            description = "Returns a paginated list of songs, sorted by the given field."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Songs retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Entity not found",
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
    @GetMapping
    public ResponseEntity<Page<SongResponse>> getAllSongs(
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam int size,

            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam int pageNumber,

            @Parameter(description = "Field name to sort by", example = "title")
            @RequestParam String sort
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        return ResponseEntity.ok(songService.findAll(pageable));
    }

    @Operation(
            summary = "Get a song by its ID",
            description = "Retrieves a song using its internal unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Song found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Song not found",
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
    public ResponseEntity<SongResponse> getSongById(
            @Parameter(description = "Internal song ID", example = "1")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(songService.findById(id));
    }


    @Operation(
            summary = "Get a song by Spotify ID",
            description = "Retrieves a song using its Spotify identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Song found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404",
                    description = "Song not found",
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
    public ResponseEntity<SongResponse> getSongBySpotifyId(
            @Parameter(description = "Spotify song ID", example = "5KawlOMHjWeUjQtnuRs22c")
            @PathVariable String spotifyId
    ) {
        return ResponseEntity.ok(songService.findBySpotifyId(spotifyId));
    }

    @Operation(
            summary = "Save a new song",
            description = "Creates a new song record and returns the saved song."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Song created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid song data",
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
    @PostMapping
    public ResponseEntity<SongResponse> saveSong(
            @Parameter(description = "Song entity to be saved")
            @RequestBody SongRequest songRequest
    ) {
        SongResponse savedSong = songService.saveSong(songRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    @Operation(
            summary = "Search for songs by keyword",
            description = "Performs a text search on songs using title or artist name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Search completed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid search parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<Page<SongResponse>> searchSongs(
            @Parameter(description = "Search query keyword", example = "Coldplay")
            @RequestParam String query,
            @Parameter(hidden = true)
            Pageable pageable
    ) {
        Page<SongResponse> songPage = songService.searchSongs(query, pageable);
        return ResponseEntity.ok(songPage);
    }

    @Operation(
            summary = "Soft delete a song by ID",
            description = "Marks a song as inactive using its internal unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Song deleted successfully",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404",
                    description = "Song not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongById(
            @Parameter(description = "Internal song ID", example = "1")
            @PathVariable Long id
    ) {
        songService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
