package com.musicspring.app.music_app.spotify.controller;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.spotify.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/search/songs")
    public ResponseEntity<List<SongEntity>> searchSongs(@RequestParam String query,
                                                        @RequestParam(defaultValue = "20") int limit,
                                                        @RequestParam(defaultValue = "0") int offset) {
        List<SongEntity> songs = spotifyService.searchSongs(query, limit, offset);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search/artists")
    public ResponseEntity<List<ArtistEntity>> searchArtists(@RequestParam String query,
                                                            @RequestParam(defaultValue = "20") int limit,
                                                            @RequestParam(defaultValue = "0") int offset) {
        List<ArtistEntity> artists = spotifyService.searchArtists(query, limit, offset);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/search/albums")
    public ResponseEntity<List<AlbumEntity>> searchAlbums(@RequestParam String query,
                                                          @RequestParam(defaultValue = "20") int limit,
                                                          @RequestParam(defaultValue = "0") int offset) {
        List<AlbumEntity> albums = spotifyService.searchAlbums(query, limit, offset);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongEntity> getSong(@PathVariable String id) {
        SongEntity song = spotifyService.getSong(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistEntity> getArtist(@PathVariable String id) {
        ArtistEntity artist = spotifyService.getArtist(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumEntity> getAlbum(@PathVariable String id) {
        AlbumEntity album = spotifyService.getAlbum(id);
        return ResponseEntity.ok(album);
    }
}
