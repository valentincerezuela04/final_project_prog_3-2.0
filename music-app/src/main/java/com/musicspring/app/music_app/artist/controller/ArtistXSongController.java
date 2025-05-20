package com.musicspring.app.music_app.artist.controller;

import com.musicspring.app.music_app.artist.model.dto.ArtistXSongRequest;
import com.musicspring.app.music_app.artist.model.dto.ArtistXSongResponse;
import com.musicspring.app.music_app.artist.service.ArtistService;
import com.musicspring.app.music_app.song.service.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/artist-songs")
public class ArtistXSongController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @PostMapping("/{artistId}/{songId}")
    public ResponseEntity<ArtistXSongResponse> addSongToArtist(@RequestBody @Valid ArtistXSongRequest artistXSongRequest) {

            ArtistXSongResponse response = artistService.createArtistSongRelationship(artistXSongRequest.getArtistId(),artistXSongRequest.getSongId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{artistId}/{songId}")
    public ResponseEntity<Void> removeSongFromArtist(@RequestBody @Valid ArtistXSongRequest request) {

            artistService.deleteArtistSongRelationship(request.getArtistId(), request.getSongId());
            return ResponseEntity.noContent().build();

    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<Page<ArtistXSongResponse>> getSongsByArtist(@PathVariable Long artistId, Pageable pageable) {

            Page<ArtistXSongResponse> songs = artistService.getSongsByArtistId(artistId,pageable);
            return ResponseEntity.ok(songs);
        }

    @GetMapping("/song/{songId}")
    public ResponseEntity<Page<ArtistXSongResponse>> getArtistsBySong(@PathVariable Long songId,Pageable pageable) {

        Page<ArtistXSongResponse> artists = artistService.getArtistsBySongId(songId,pageable);
        return ResponseEntity.ok(artists);
    }

}


