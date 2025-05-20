package com.musicspring.app.music_app.album.controller;

import com.musicspring.app.music_app.album.model.dto.AlbumRequest;
import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.service.AlbumService;
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
public class AlbumController {
    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse>getAlbumById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(albumService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<AlbumResponse>> getAllAlbums (@RequestParam int size, @RequestParam int pageNumber, @RequestParam String sort){
        Pageable pageable = PageRequest.of(pageNumber,size, Sort.by(sort));
        return ResponseEntity.ok(albumService.findAll(pageable));
    }

    @GetMapping("/spotify/{spotifyId}")
    public ResponseEntity<AlbumResponse> getAlbumBySpotifyId (@PathVariable String spotifyId){
        try {
            return ResponseEntity.ok(albumService.findBySpotifyId(spotifyId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumResponse> saveAlbum (@RequestBody AlbumRequest albumEntity){
        return ResponseEntity.ok(albumService.save(albumEntity));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AlbumResponse>> searchAlbums (@RequestParam String query, @RequestParam int size, @RequestParam int pageNumber, @RequestParam String sort){
        Pageable pageable = PageRequest.of(pageNumber,size, Sort.by(sort));
        return ResponseEntity.ok(albumService.search(query,pageable));
    }

}

