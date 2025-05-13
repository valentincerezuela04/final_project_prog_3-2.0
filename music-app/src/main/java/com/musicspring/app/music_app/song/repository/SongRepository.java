package com.musicspring.app.music_app.song.repository;

import com.musicspring.app.music_app.song.model.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findBySpotifyId(String spotifyId);
    List<SongEntity> findByNameContainingIgnoreCaseOrArtistNameContainingIgnoreCase(
            String name, String artistName);

    // Page<Song> findByArtistNameContainingIgnoreCase(String artistName, Pageable pageable);
    // Page implementation soon

}
