package com.musicspring.app.music_app.artist.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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
    private String biography;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "song",cascade = CascadeType.ALL)
    private Set<ArtistXSongEntity> artistSongs = new HashSet<>();


}
