package com.musicspring.app.music_app.artist.model.dto;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private List<ArtistXSongDto> artistSongs = new ArrayList<>();

}
