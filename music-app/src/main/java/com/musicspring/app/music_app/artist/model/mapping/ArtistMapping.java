package com.musicspring.app.music_app.artist.model.mapping;

import com.musicspring.app.music_app.artist.model.dto.ArtistDto;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;

public class ArtistMapping {
    public static ArtistDto toDto(ArtistEntity entity){
        return ArtistDto.builder()
                .artistId(entity.getArtistId())
                .name(entity.getName())
                .country(entity.getCountry())
                .age(entity.getAge())
                .biography(entity.getBigraphy())
                .build();
    }

    private static ArtistEntity toEntity(ArtistDto dto){
        return ArtistEntity.builder()
                .artistId(dto.getArtistId())
                .name(dto.getName())
                .country(dto.getCountry())
                .age(dto.getAge())
                .bigraphy(dto.getBiography())
                .build();
    }


    public static ArtistDto toDtoWithList(ArtistEntity entity){
        return ArtistDto.builder()
                .artistId(entity.getArtistId())
                .name(entity.getName())
                .country(entity.getCountry())
                .age(entity.getAge())
                .biography(entity.getBigraphy())
/*                .artistSongs(
                        entity.getArtistSongs() != null
                        ? entity.getArtistSongs().stream()
                                .map(ArtistXSongMapping::toDto)
                                .collect(Collectors.toList())
                        ::null
                )*/
                .build();
    }

    private static ArtistEntity toEntityWithList(ArtistDto dto){
        return ArtistEntity.builder()
                .artistId(dto.getArtistId())
                .name(dto.getName())
                .country(dto.getCountry())
                .age(dto.getAge())
                .bigraphy(dto.getBiography())
                .build();
    }



}
