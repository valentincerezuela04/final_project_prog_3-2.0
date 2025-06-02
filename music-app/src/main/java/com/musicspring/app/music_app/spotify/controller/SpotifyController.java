package com.musicspring.app.music_app.spotify.controller;

import com.musicspring.app.music_app.model.dto.AlbumRequest;
import com.musicspring.app.music_app.model.dto.ArtistRequest;
import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.model.dto.SongRequest;
import com.musicspring.app.music_app.spotify.service.SpotifyService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spotify")
@Tag(name = "Spotify", description = "API for Spotify integration operations")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @Operation(
            summary = "Search songs on Spotify",
            description = "Searches for songs on Spotify matching the provided query string and returns a paginated result."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Songs retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
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
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/search/songs")
    public ResponseEntity<Page<SongRequest>> searchSongs(
            @Parameter(description = "Search query string", required = true, example = "Bohemian Rhapsody")
            @RequestParam String query,
            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SongRequest> songs = spotifyService.searchSongs(query, pageable);
        return ResponseEntity.ok(songs);
    }

    @Operation(
            summary = "Search artists on Spotify",
            description = "Searches for artists on Spotify matching the provided query string and returns a paginated result."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artists retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
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
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/search/artists")
    public ResponseEntity<Page<ArtistRequest>> searchArtists(
            @Parameter(description = "Search query string", required = true, example = "Queen")
            @RequestParam String query,
            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArtistRequest> artists = spotifyService.searchArtists(query, pageable);
        return ResponseEntity.ok(artists);
    }

    @Operation(
            summary = "Search albums on Spotify",
            description = "Searches for albums on Spotify matching the provided query string and returns a paginated result."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Albums retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
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
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/search/albums")
    public ResponseEntity<Page<AlbumRequest>> searchAlbums(
            @Parameter(description = "Search query string", required = true, example = "A Night at the Opera")
            @RequestParam String query,
            @Parameter(description = "Number of items per page", example = "20")
            @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AlbumRequest> albums = spotifyService.searchAlbums(query, pageable);
        return ResponseEntity.ok(albums);
    }

    @Operation(
            summary = "Get a specific song from Spotify",
            description = "Retrieves detailed information about a song identified by its Spotify ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Song retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SongRequest.class)
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
                    responseCode = "500",
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/songs/{id}")
    public ResponseEntity<SongRequest> getSong(
            @Parameter(description = "Spotify ID of the song to retrieve", required = true, example = "4u7EnebtmKWzUH433cf5Qv")
            @PathVariable String id) {
        SongRequest song = spotifyService.getSong(id);
        return ResponseEntity.ok(song);
    }

    @Operation(
            summary = "Get a specific artist from Spotify",
            description = "Retrieves detailed information about an artist identified by their Spotify ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Artist retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ArtistRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artist not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistRequest> getArtist(
            @Parameter(description = "Spotify ID of the artist to retrieve", required = true, example = "1dfeR4HaWDbWqFHLkxsg1d")
            @PathVariable String id) {
        ArtistRequest artist = spotifyService.getArtist(id);
        return ResponseEntity.ok(artist);
    }

    @Operation(
            summary = "Get a specific album from Spotify",
            description = "Retrieves detailed information about an album identified by its Spotify ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Album retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlbumRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Album not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error or Spotify API error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumRequest> getAlbum(
            @Parameter(description = "Spotify ID of the album to retrieve", required = true, example = "6i6folBtxKV28WX3msQ4FE")
            @PathVariable String id) {
        AlbumRequest album = spotifyService.getAlbum(id);
        return ResponseEntity.ok(album);
    }
}
