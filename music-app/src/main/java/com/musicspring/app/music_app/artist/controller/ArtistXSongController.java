package com.musicspring.app.music_app.artist.controller;

import com.musicspring.app.music_app.artist.model.dto.ArtistXSongRequest;
import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.service.ArtistService;
import com.musicspring.app.music_app.song.service.SongService;
import com.musicspring.app.music_app.exceptions.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Artist-Song Management", description = "Endpoints for managing the relationship between artists and songs")
@RestController
@RequestMapping("/api/v1/artist-songs")
public class ArtistXSongController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @Operation(summary = "Assign a song to an artist", description = "Creates a relationship between a song and an artist.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Relationship created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArtistXSongResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @PostMapping("/{artistId}/{songId}")
    public ResponseEntity<ArtistXSongResponse> addSongToArtist(
            @Parameter(description = "Request body with artistId and songId", required = true)
            @RequestBody @Valid ArtistXSongRequest artistXSongRequest) {

        ArtistXSongResponse response = artistService.createArtistSongRelationship(
                artistXSongRequest.getArtistId(),
                artistXSongRequest.getSongId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Remove a song from an artist", description = "Deletes the relationship between an artist and a song.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Relationship deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relationship not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @DeleteMapping("/{artistId}/{songId}")
    public ResponseEntity<Void> removeSongFromArtist(
            @Parameter(description = "Request body with artistId and songId", required = true)
            @RequestBody @Valid ArtistXSongRequest request) {

        artistService.deleteArtistSongRelationship(request.getArtistId(), request.getSongId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get songs by artist", description = "Retrieves a paginated list of songs assigned to a specific artist.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Songs retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artist not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<ArtistXSongResponse>> getSongsByArtist(
            @Parameter(description = "ID of the artist", example = "1", required = true)
            @PathVariable Long artistId,
            Pageable pageable) {

        Page<ArtistXSongResponse> songs = artistService.getSongsByArtistId(artistId, pageable);
        return ResponseEntity.ok(songs);
    }

    @Operation(summary = "Get artists by song", description = "Retrieves a paginated list of artists assigned to a specific song.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artists retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Song not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @GetMapping("/song/{songId}")
    public ResponseEntity<Page<ArtistXSongResponse>> getArtistsBySong(
            @Parameter(description = "ID of the song", example = "1", required = true)
            @PathVariable Long songId,
            Pageable pageable) {

        Page<ArtistXSongResponse> artists = artistService.getArtistsBySongId(songId, pageable);
        return ResponseEntity.ok(artists);
    }
}
