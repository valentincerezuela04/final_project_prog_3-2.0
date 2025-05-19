package com.musicspring.app.music_app.song.controller;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<Page<SongResponse>> getAllSongs(
            @RequestParam int size,
            @RequestParam int pageNumber,
            @RequestParam String sort
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        return ResponseEntity.ok(songService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.findById(id));
    }

    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<SongResponse> getSongBySpotifyId(@PathVariable String spotifyId) {
        return ResponseEntity.ok(songService.findBySpotifyId(spotifyId));
    }

    @PostMapping
    public ResponseEntity<SongResponse> saveSong(@RequestBody SongEntity songEntity) {
        SongResponse savedSong = songService.saveSong(songEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSong);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SongResponse>> searchSongs(@RequestParam String query,
                                                          Pageable pageable) {
        Page<SongResponse> songPage = songService.searchSongs(query, pageable);
        return ResponseEntity.ok(songPage);
    }
}
