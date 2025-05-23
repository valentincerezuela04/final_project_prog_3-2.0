package com.musicspring.app.music_app.album.controller;

import com.musicspring.app.music_app.album.model.dto.AlbumRequest;
import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.service.AlbumService;
import com.musicspring.app.music_app.exceptions.ErrorDetails;
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


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
@Tag(name = "Albums", description = "Endpoints for managing albums")
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "Get album by ID", description = "Retrieve a single album using its internal database ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "404", description = "Album not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> getAlbumById(
            @Parameter(description = "Internal ID of the album", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }


    @Operation(summary = "Get all albums", description = "Retrieve a paginated list of all albums, with optional sorting.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Albums retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/all")
    public ResponseEntity<Page<AlbumResponse>> getAllAlbums(
            @Parameter(description = "Number of items per page", example = "10") @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0") @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "title") @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        return ResponseEntity.ok(albumService.findAll(pageable));
    }


    @Operation(summary = "Get album by Spotify ID", description = "Retrieve a single album using its Spotify ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "404", description = "Album not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<AlbumResponse> getAlbumBySpotifyId(
            @Parameter(description = "Spotify ID of the album", example = "6z4NLXyHPga1UmSJsPK7G1")
            @PathVariable String spotifyId) {
        return ResponseEntity.ok(albumService.findBySpotifyId(spotifyId));
    }


    @Operation(summary = "Create new album", description = "Creates a new album using the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    public ResponseEntity<AlbumResponse> createAlbum(
            @Parameter(description = "Album creation request payload", required = true)
            @RequestBody AlbumRequest albumRequest) {
        return ResponseEntity.ok(albumService.save(albumRequest));
    }


    @Operation(summary = "Search albums", description = "Search for albums by title or other fields, with pagination and sorting.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Albums retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search")
    public ResponseEntity<Page<AlbumResponse>> searchAlbums(
            @Parameter(description = "Search query string", example = "nostalgia") @RequestParam String query,
            @Parameter(description = "Number of items per page", example = "10") @RequestParam int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0") @RequestParam int pageNumber,
            @Parameter(description = "Field to sort by", example = "releaseDate") @RequestParam String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        return ResponseEntity.ok(albumService.search(query, pageable));
    }


    @Operation(summary = "Delete an album by ID", description = "Deletes the album corresponding to the provided ID. If the album does not exist, a 404 response is returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Album deleted successfully. No content is returned."),
            @ApiResponse(responseCode = "404", description = "Album not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlbum(
            @Parameter(description = "ID of the album to delete", required = true, example = "1")
            @PathVariable Long id
    ) {
        albumService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

