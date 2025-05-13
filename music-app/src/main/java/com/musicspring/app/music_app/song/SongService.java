package com.musicspring.app.music_app.song;

import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongEntity> findAll(){
        return songRepository.findAll();
    }

    public Optional<SongEntity> findById(Long id){
        return songRepository.findById(id);
    }

    public Optional<SongEntity> findBySpotifyId(String spotifyId){
        return songRepository.findBySpotifyId(spotifyId);
    }

    public void softDelete(Long id){
        Optional<SongEntity> songOpt = songRepository.findById(id);

        if(songOpt.isEmpty()){
            System.out.println("Cambiar por una excepcion");
        }

        SongEntity song = songOpt.get();
        

    }
}
