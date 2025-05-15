package com.musicspring.app.music_app.album.controller;

import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.sercive.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private AlbumService albumService;
    private AlbumMapper albumMapper;

    public AlbumController(AlbumService albumService, AlbumMapper albumMapper) {
        this.albumService = albumService;
        this.albumMapper = albumMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse>getAlbumById(@PathVariable Long id){
        AlbumEntity albumEntity = albumService.findById(id);
        return ResponseEntity.ok(albumMapper.toResponse(albumEntity));
    }

    @GetMapping
    public ResponseEntity<Page<AlbumResponse>> getAllAlbums (Pageable pageable){
        Page<AlbumEntity> albumPage = albumService.findAll(pageable);
        Page<AlbumResponse> albumResponse = albumMapper.toResponsePage(albumPage);
        return ResponseEntity.ok(albumResponse);
    }

    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<AlbumResponse> getAlbumBySpotifyId (@PathVariable String spotifyId){
        AlbumEntity albumEntity = albumService.findBySpotifyId(spotifyId);
        return  ResponseEntity.ok(albumMapper.toResponse(albumEntity));
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumResponse> createAlbum (@RequestBody AlbumEntity albumEntity){
        AlbumEntity savedAlbum = albumService.save(albumEntity);
        return new ResponseEntity<>(albumMapper.toResponse(savedAlbum), HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AlbumResponse>> searchAlbums (@RequestParam String query, Pageable pageable){
        Page<AlbumEntity> albumPage = albumService.search(query, pageable);
        Page<AlbumResponse> albumResponse = albumMapper.toResponsePage(albumPage);
        return ResponseEntity.ok(albumResponse);
    }

}

