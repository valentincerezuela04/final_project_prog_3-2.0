package com.musicspring.app.music_app.controller;

import com.musicspring.app.music_app.model.dto.ArtistRequest;
import com.musicspring.app.music_app.model.dto.ArtistResponse;
import com.musicspring.app.music_app.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.service.ArtistService;
import com.musicspring.app.music_app.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Artist", description = "Operations related to musical artists")
@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(summary = "Retrieve all artists", description = "Fetches a paginated list of all artists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artists retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ArtistResponse>> getAllArtists(Pageable pageable) {
        Page<ArtistResponse> response = artistService.getAllArtists(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve an artist by ID", description = "Fetches the details of a specific artist using their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist found successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistResponse.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(
            @Parameter(description = "ID of the artist to retrieve", example = "1")
            @PathVariable Long id) {
        try {
            ArtistResponse artist = artistService.getArtistResponseById(id);
            return ResponseEntity.ok(artist);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new artist", description = "Registers a new artist with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Artist created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping
    public ResponseEntity<ArtistResponse> addArtist(
            @Parameter(description = "Details of the artist to create", required = true)
            @Valid @RequestBody ArtistRequest artistRequest) {
        ArtistResponse saved = artistService.save(artistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Delete an artist by ID", description = "Deletes the artist identified by the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Artist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(
            @Parameter(description = "ID of the artist to delete", example = "1")
            @PathVariable Long id) {
        try {
            artistService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Retrieve an artist along with their songs", description = "Fetches an artist's details including a list of their songs.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist and songs retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistWithSongsResponse.class))),
            @ApiResponse(responseCode = "404", description = "Artist not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/{id}/songs")
    public ResponseEntity<ArtistWithSongsResponse> getArtistWithSongs(
            @Parameter(description = "ID of the artist", example = "1")
            @PathVariable Long id) {
        try {
            ArtistWithSongsResponse response = artistService.getArtistWithSongs(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Search artists by name", description = "Searches for artists matching the provided name (partial or full).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matching artists retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid name parameter",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtistsByName(
            @Parameter(description = "Name or partial name to search for", example = "Taylor")
            @RequestParam String name) {
        List<ArtistResponse> artists = artistService.findByName(name);
        return ResponseEntity.ok(artists);
    }
}
