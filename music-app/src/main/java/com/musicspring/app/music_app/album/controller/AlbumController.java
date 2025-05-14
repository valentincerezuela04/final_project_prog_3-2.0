package com.musicspring.app.music_app.album.controller;

import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.service.AlbumService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    private AlbumService albumService;

//    @GetMapping("/albums")
//    public ResponseEntity<List<AlbumResponse>> getAllAlbums(Pageable pageable) {
//        Page<AlbumEntity> songPage = albumService.findAll(pageable);
//    }
}
