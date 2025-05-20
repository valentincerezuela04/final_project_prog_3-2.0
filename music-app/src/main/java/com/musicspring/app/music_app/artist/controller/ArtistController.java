package com.musicspring.app.music_app.artist.controller;

import com.musicspring.app.music_app.artist.model.dto.ArtistRequest;
import com.musicspring.app.music_app.artist.model.dto.ArtistResponse;
import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsResponse;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.mapper.ArtistMapper;
import com.musicspring.app.music_app.artist.service.ArtistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<Page<ArtistResponse>> getAllArtists(Pageable pageable) {
        Page<ArtistResponse> response = artistService.getAllArtists(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable Long id) {
        try {
            ArtistResponse artist = artistService.findById(id);
            return ResponseEntity.ok(artist);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ArtistResponse> addArtist(@Valid @RequestBody ArtistRequest artistRequest) {
        ArtistResponse saved = artistService.save(artistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        try {
            artistService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<ArtistWithSongsResponse> getArtistWithSongs(@PathVariable Long id) {
        try {
            ArtistWithSongsResponse response = artistService.getArtistWithSongs(id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistResponse>> searchArtistsByName(@RequestParam String name) {
        List<ArtistResponse> artists = artistService.findByName(name);
        return ResponseEntity.ok(artists);
    }
}
