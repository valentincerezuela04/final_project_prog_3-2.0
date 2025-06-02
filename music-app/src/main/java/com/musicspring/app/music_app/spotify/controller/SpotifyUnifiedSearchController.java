package com.musicspring.app.music_app.spotify.controller;

import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.spotify.model.UnifiedSearchResponse;
import com.musicspring.app.music_app.spotify.service.SpotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/spotify/unified-search")
@Tag(name = "Spotify Unified Search", description = "API for searching across Spotify with a single endpoint")
public class SpotifyUnifiedSearchController {

    private final SpotifyService spotifyService;

    @Autowired
    public SpotifyUnifiedSearchController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Operation(
            summary = "Unified search in Spotify",
            description = "Searches for songs, artists, and albums in Spotify with a single query, " +
                    "returning grouped results from all three categories."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Search successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnifiedSearchResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid search parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<UnifiedSearchResponse> search(
            @Parameter(description = "Text to search for in songs, artists and albums", required = true, example = "Bohemian Rhapsody")
            @RequestParam String query,
            @Parameter(description = "Page size for each category", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page) {
        
        Pageable pageable = PageRequest.of(page, size);
        UnifiedSearchResponse results = spotifyService.searchAll(query, pageable);
        return ResponseEntity.ok(results);
    }
}
