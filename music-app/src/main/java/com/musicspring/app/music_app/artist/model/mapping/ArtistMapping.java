package com.musicspring.app.music_app.artist.model.mapping;

import com.musicspring.app.music_app.artist.model.dto.ArtistDto;
import com.musicspring.app.music_app.artist.model.dto.ArtistWithSongsDto;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.mapper.SongMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArtistMapping {
    private  final  SongMapper songMapper;

    @Autowired
    public ArtistMapping(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public  ArtistDto toDto(ArtistEntity entity){


        return ArtistDto.builder()
                .artistId(entity.getArtistId())
                .name(entity.getName())
                .country(entity.getCountry())
                .age(entity.getAge())
                .biography(entity.getBigraphy())
                .build();
    }

    private  ArtistEntity toEntity(ArtistDto dto){
        return ArtistEntity.builder()
                .artistId(dto.getArtistId())
                .name(dto.getName())
                .country(dto.getCountry())
                .age(dto.getAge())
                .bigraphy(dto.getBiography())
                .build();
    }

    public  ArtistWithSongsDto toDTO(ArtistEntity artist, List<ArtistXSongEntity> relations) {
        List<SongResponse> songs = relations.stream()
                .filter(rel -> rel.getArtist().getArtistId().equals(artist.getArtistId()))
                .map(ArtistXSongEntity::getSong)
                .map(songMapper::toResponse)
                .toList();

        return ArtistWithSongsDto.builder()
                .artistId(artist.getArtistId())
                .name(artist.getName())
                .country(artist.getCountry())
                .age(artist.getAge())
                .biography(artist.getBigraphy())
                .songs(songs)
                .build();
    }




}
