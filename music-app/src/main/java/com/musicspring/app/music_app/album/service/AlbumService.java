package com.musicspring.app.music_app.album.service;

import com.musicspring.app.music_app.album.model.dto.AlbumRequest;
import com.musicspring.app.music_app.album.model.dto.AlbumResponse;
import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.album.model.mapper.AlbumMapper;
import com.musicspring.app.music_app.album.repository.AlbumRepository;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.service.ArtistService;
import com.musicspring.app.music_app.shared.IService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService  {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistService artistService;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper, ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.artistService = artistService;
    }


    public Page<AlbumResponse> findAll(Pageable pageable) {
        return albumMapper.toResponsePage(albumRepository.findAll(pageable));
    }

    public AlbumResponse findById(Long id) {
        return albumMapper.toResponse(albumRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Album with ID " + id + " was not found.")));
    }

    public void deleteById(Long id) {
        AlbumEntity albumEntity = albumRepository.findById(id).get();
        if (!albumEntity.getActive()){
            System.out.println("This album has already been deleted.");
        }else{
            albumEntity.setActive(false);
            albumRepository.save(albumEntity);
        }
    }

    public AlbumResponse save(AlbumEntity albumEntity) {
        albumRepository.save(albumEntity);
        return albumMapper.toResponse(albumEntity);
    }

    public AlbumResponse findBySpotifyId(String spotifyId){
        return albumMapper.toResponse(albumRepository.findBySpotifyId(spotifyId).orElseThrow(()
                -> new EntityNotFoundException("Album with Spotify ID " + spotifyId + " was not found.")));
    }

    public Page<AlbumResponse> search (String query, Pageable pageable){
        List<AlbumEntity> albumEntityList =  albumRepository.findByTitleContainingIgnoreCase
                (query, pageable);
        Page<AlbumEntity> albumEntityPage = albumMapper.toEntityPage(albumEntityList,pageable);
        return albumMapper.toResponsePage(albumEntityPage);
    }

    public AlbumResponse createAlbum (AlbumRequest albumRequest){
        /// verifico que el artista existAa
        ArtistEntity artistEntity = artistService.findById(albumRequest.getArtistId());
        AlbumEntity albumEntity = albumMapper.responseToEntity(findBySpotifyId(albumRequest.getSpotifyId()),artistEntity);
        return albumMapper.toResponse(albumRepository.save(albumEntity));
    }
}
