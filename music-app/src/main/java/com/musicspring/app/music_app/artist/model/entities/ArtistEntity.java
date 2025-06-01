package com.musicspring.app.music_app.artist.model.entities;

import com.musicspring.app.music_app.song.model.entity.SongEntity;
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

    @Column(name = "followers")
    private Integer followers;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "artist_song",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<SongEntity> songs = new HashSet<>();


}
