package com.musicspring.app.music_app.artist.model.entities;


import jakarta.persistence.*;
import lombok.*;


/// Hay dos opciones para encarar la tabla intermedia entre artistas y canciones

/// Opcion 1:
/// dejaria esta clase si es que vamos a agregar un atributo mas sino no tiene sentido tenerla seria hacer un @manyToMany con la tabla songs
///(osea q ya la tabla intermedia la crea jpa solo osea no necesitamos la ArtistXSongEntity) y ademas le agregaria una clave compuesta con las dos Fk por
///q tener el id de esta tabla no tiene mucho sentido no cumple ninguna funcion y ya lo remplazariamos con la clave compuesta

///Opcion 2:
/// en el caso de agregar mas atributos si estaria bien dejar esta clase como esta hecha
/// ejemplos de atributos q se le podriasn agregar:
/// personalmente agregaria:collaboration_date (DATE) y contribution_percentage (DECIMAL(5,2)) q esta ultima la podemos usar para sacar un porcentaje en la tabla de porcentajes
/*
🎯 Atributos útiles para la tabla intermedia Artist_x_Song
role (VARCHAR)

Ejemplo: 'vocalist', 'producer', 'guitarist', 'composer'

Sirve para detallar qué hizo el artista en esa canción.

contribution_percentage (DECIMAL(5,2))

Porcentaje estimado de aporte del artista (para royalties o crédito).

collaboration_date (DATE)

Fecha en que se grabó o colaboró la canción.

is_featured (BOOLEAN)

Si el artista fue un "featured" en esa canción (true para invitados).

notes (TEXT o VARCHAR(1000))

Observaciones o anotaciones extras sobre esa colaboración.

*/
@Entity
@Table(name = "artist_x_song")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArtistXSongEntity {

    @EmbeddedId
    private ArtistXSongId artitsXSongId;


    @ManyToOne
    @MapsId("artistId")
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;

/*   @ManyToOne
    @Maps("songId")
    @JoinColumn(name = "song_id")
    //private Song song;*/


}
