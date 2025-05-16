package com.musicspring.app.music_app.artist.controller;

import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.service.ArtistService;
import com.musicspring.app.music_app.song.service.SongService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artist-songs")
public class ArtistXSongController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @PostMapping("/{artistId}/{songId}")
    public ResponseEntity<ArtistXSongResponse> addSongToArtist(
            @PathVariable Long artistId,
            @PathVariable Long songId) {
        try {
            ArtistXSongResponse response = artistService.createArtistSongRelationship(artistId, songId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{artistId}/{songId}")
    public ResponseEntity<Void> removeSongFromArtist(
            @PathVariable Long artistId,
            @PathVariable Long songId) {
        try {
            artistService.deleteArtistSongRelationship(artistId, songId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<ArtistXSongResponse>> getSongsByArtist(@PathVariable Long artistId) {
        try {
            List<ArtistXSongResponse> songs = artistService.getSongsByArtistId(artistId);
            return ResponseEntity.ok(songs);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<ArtistXSongResponse>> getArtistsBySong(@PathVariable Long songId) {
        try {
            List<ArtistXSongResponse> artists = artistService.getArtistsBySongId(songId);
            return ResponseEntity.ok(artists);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}