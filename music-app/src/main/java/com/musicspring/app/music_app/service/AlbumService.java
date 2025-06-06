package com.musicspring.app.music_app.service;

import com.musicspring.app.music_app.model.dto.request.AlbumRequest;
import com.musicspring.app.music_app.model.dto.response.AlbumResponse;
import com.musicspring.app.music_app.model.entity.AlbumEntity;
import com.musicspring.app.music_app.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.repository.AlbumRepository;
import com.musicspring.app.music_app.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService  {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;

    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.artistRepository = artistRepository;
    }


    public Page<AlbumResponse> findAll(Pageable pageable) {
        return albumMapper.toResponsePage(albumRepository.findAll(pageable));
    }

    public AlbumResponse findById(Long id) {
        return albumMapper.toResponse(albumRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Album with ID " + id + " not found.")));
    }

    public AlbumEntity findEntityById(Long id) {
        return albumRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Album with ID " + id + " not found."));
    }

    public void deleteById(Long id) {
        AlbumEntity albumEntity = albumRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Album with ID " + id + " not found."));
        if (!albumEntity.getActive()){
            System.out.println("This album has already been deleted.");
        }else{
            albumEntity.setActive(false);
            albumRepository.save(albumEntity);
        }
    }

    public AlbumResponse saveAlbum(AlbumRequest albumRequest) {
        AlbumEntity albumEntity = albumMapper.requestToEntity(albumRequest);
        return albumMapper.toResponse(albumRepository.save(albumEntity));
    }

    public AlbumResponse findBySpotifyId(String spotifyId){
        return albumMapper.toResponse(albumRepository.findBySpotifyId(spotifyId).orElseThrow(()
                -> new EntityNotFoundException("Album with Spotify ID " + spotifyId + " not found.")));
    }

    public Page<AlbumResponse> searchAlbums(String query, Pageable pageable){
        List<AlbumEntity> albumEntityList =  albumRepository.findByTitleContainingIgnoreCase
                (query, pageable);
        Page<AlbumEntity> albumEntityPage = albumMapper.toEntityPage(albumEntityList,pageable);
        return albumMapper.toResponsePage(albumEntityPage);
    }

}
