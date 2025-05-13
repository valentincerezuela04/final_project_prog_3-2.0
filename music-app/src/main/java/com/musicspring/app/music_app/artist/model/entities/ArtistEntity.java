package com.musicspring.app.music_app.artist.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;
    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name =  "age")
    private int age;

    @Column(name = "biography")
    private String bigraphy;

    @OneToMany(mappedBy = "song",cascade = CascadeType.ALL)
    private List<ArtistXSongEntity> artistSongs = new ArrayList<>();


}
