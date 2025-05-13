package com.musicspring.app.music_app.artist.service;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ArtistXSongService {

        private final ArtistXSongRepository artistXSongRepository;

        @Autowired
        public ArtistXSongService(ArtistXSongRepository artistXSongRepository) {
            this.artistXSongRepository = artistXSongRepository;
        }

        public List<ArtistXSongEntity> getAll(){
            return artistXSongRepository.findAll();
        }

        public Optional<ArtistXSongEntity> getbyId(Long id){
            return artistXSongRepository.findById(id);
        }

        public ArtistXSongEntity save(ArtistXSongEntity artist){
            return artistXSongRepository.save(artist);

        }

        public void delete(Long id){
            artistXSongRepository.deleteById(id);
        }
}
