package com.musicspring.app.music_app.artist.controller;


import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.service.ArtistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }



    @GetMapping
    public Page<ArtistEntity> getAllArtists(Pageable pageable) {
        try {
            return artistService.findAll(pageable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch artists", e);
        }
    }


    @GetMapping("/{id}")
    public ArtistEntity getArtistById(@PathVariable Long id) {

        try{
            return artistService.findById(id);

        }catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Artist not found");
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistEntity addArtist(@Valid @RequestBody ArtistEntity artistEntity) {
        return artistService.save(artistEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        if (!artistService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist not found");
        }
        artistService.deleteById(id);
    }

}
