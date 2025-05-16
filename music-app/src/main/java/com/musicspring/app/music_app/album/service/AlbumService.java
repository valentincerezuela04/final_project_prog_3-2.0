package com.musicspring.app.music_app.album.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.repository.AlbumRepository;
import com.musicspring.app.music_app.shared.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumService implements IService<AlbumEntity> {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Page<AlbumEntity> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    @Override
    public AlbumEntity findById(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Album with ID " + id + " was not found."));
    }

    @Override
    public void deleteById(Long id) {
        AlbumEntity albumEntity = findById(id);
        if (!albumEntity.getActive()){
            System.out.println("This album has already been deleted.");
        }else{
            albumEntity.setActive(false);
            albumRepository.save(albumEntity);
        }
    }

    @Override
    public AlbumEntity save(AlbumEntity albumEntity) {
        albumRepository.save(albumEntity);
        return albumEntity;
    }

    public Optional<AlbumEntity> findBySpotifyId(String spotifyId){
        return albumRepository.findBySpotifyId(spotifyId);
    }
}
