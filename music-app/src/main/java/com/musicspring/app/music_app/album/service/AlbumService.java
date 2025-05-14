package com.musicspring.app.music_app.album.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.repository.AlbumRepository;
import org.hibernate.query.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

//    public Page<AlbumEntity> findAll(Pageable pageable) {
//        return albumRepository.findAll(pageable);
//    }

    public AlbumEntity findById(Long id) {
        return albumRepository.findById(id).orElseThrow(()-> new RuntimeException("Album with ID: " + id + " was not found."));
    }

    public List<AlbumEntity> getByTitle(String title) {
        return albumRepository.findByTitleIgnoreCase(title);
    }

    public AlbumEntity save(AlbumEntity album) {
        return albumRepository.save(album);
    }

    public Optional<AlbumEntity> findBySpotifyId(String spotifyId) {
        return albumRepository.findBySpotifyId(spotifyId);
    }

    public void delete(Long id) {
        AlbumEntity album = findById(id);
        if (!album.getActive()){
            System.out.println("This album has already been deleted.");
        }
        else {
            album.setActive(false);
            albumRepository.save(album);
        }
    }
}
