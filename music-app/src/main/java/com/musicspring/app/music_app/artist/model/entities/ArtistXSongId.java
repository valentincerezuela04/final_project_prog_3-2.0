package com.musicspring.app.music_app.artist.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArtistXSongId implements Serializable {

    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "song_id")
    private Long songId;

}
