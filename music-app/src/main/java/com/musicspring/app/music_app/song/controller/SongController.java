package com.musicspring.app.music_app.song.controller;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import com.musicspring.app.music_app.song.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private SongMapper songMapper;

    @GetMapping("/songs")
    public ResponseEntity<List<SongResponse>> getAllSongs(Pageable pageable) {
        Page<SongEntity> songPage = songService.findAll(pageable);
        return ResponseEntity.ok(songMapper.toResponseList(songPage.getContent()));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {
            SongEntity songEntity = songService.findById(id);
            return ResponseEntity.ok(songMapper.toResponse(songEntity));
    }

    @GetMapping("/songs/spotify/{spotifyId}")
    public ResponseEntity<SongResponse> getSongBySpotifyId(@PathVariable String spotifyId) {
        SongEntity song = songService.findBySpotifyId(spotifyId);
        return ResponseEntity.ok(songMapper.toResponse(song));
    }

    @PostMapping("/songs")
    public ResponseEntity<SongResponse> createSong(@RequestBody SongEntity songEntity) {
        SongEntity savedSong = songService.save(songEntity);
        return new ResponseEntity<>(songMapper.toResponse(savedSong), HttpStatus.CREATED);
    }

    @GetMapping("/songs/search")
    public ResponseEntity<List<SongResponse>> searchSongs(@RequestParam String query) {
        List<SongEntity> songs = songService.search(query);
        return ResponseEntity.ok(songMapper.toResponseList(songs));
    }
}
