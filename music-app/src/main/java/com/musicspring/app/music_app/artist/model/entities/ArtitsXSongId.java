package com.musicspring.app.music_app.artist.model.entities;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ArtitsXSongId {
    private Long artistId;
    private Long songId;

}
