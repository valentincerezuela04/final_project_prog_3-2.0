package com.musicspring.app.music_app.album.controller;

import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumMapper albumMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse>getAlbumsById(@PathVariable Long id){
        try{
            AlbumEntity albumEntity = albumService.findById(id);
            return ResponseEntity.ok(albumMapper.toResponse(albumEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlbumResponse>> getAllAlbums (Pageable pageable){
        Page<AlbumEntity> albumPage = albumService.findAll(pageable);
        return ResponseEntity.ok(albumMapper.toResponseList(albumPage.getContent()));
    }

    @GetMapping("/albums/spotify/{spotifyId}")
    public ResponseEntity<AlbumResponse> getAlbumBySpotifyId (@PathVariable String spotifyId){
        Optional<AlbumEntity> optAlbum = albumService.findBySpotifyId(spotifyId);
        return  optAlbum.map(value -> ResponseEntity.ok(albumMapper.toResponse(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumResponse> createAlbum (@RequestBody AlbumEntity albumEntity){
        AlbumEntity savedAlbum = albumService.save(albumEntity);
        return new ResponseEntity<>(albumMapper.toResponse(savedAlbum), HttpStatus.CREATED);
    }

}

