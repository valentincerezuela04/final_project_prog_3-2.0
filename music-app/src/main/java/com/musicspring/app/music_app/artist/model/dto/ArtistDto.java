package com.musicspring.app.music_app.artist.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistDto {
    private Long artistId;
    private String name;
    private String country;
    private int age;
    private String biography;



}
