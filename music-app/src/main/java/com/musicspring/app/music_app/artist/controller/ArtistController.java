package com.musicspring.app.music_app.artist.controller;


import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ArtistEntity> getAllArtists(){
        try{
          return artistService.getAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Error to get artists");
        }
    }

    @GetMapping("/{id}")
    public ArtistEntity getArtistById(@PathVariable Long id){

            return artistService.getbyId(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"The artist was not found"));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistEntity addArtist(@RequestBody ArtistEntity artistEntity){
        System.out.println("Saving artist " + artistEntity);
        return artistService.save(artistEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id){
        artistService.delete(id);
    }



}
