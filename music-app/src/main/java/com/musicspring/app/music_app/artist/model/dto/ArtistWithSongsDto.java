package com.musicspring.app.music_app.artist.model.dto;

import com.musicspring.app.music_app.song.model.dto.SongResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistWithSongsDto {
    private Long artistId;
    private String name;
    private String country;
    private int age;
    private String biography;

    private List<SongResponse> songs;


}
