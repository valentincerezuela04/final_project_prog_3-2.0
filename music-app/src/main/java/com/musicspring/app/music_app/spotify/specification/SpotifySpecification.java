package com.musicspring.app.music_app.spotify.specification;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class SpotifySpecification {

    public static Specification<SongEntity> songWithNameContaining(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                query == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + query.toLowerCase() + "%");
    }

    public static Specification<SongEntity> songWithArtistContaining(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                query == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("artistName")),
                        "%" + query.toLowerCase() + "%");
    }

    public static Specification<SongEntity> songWithAlbumContaining(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                query == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("albumName")),
                        "%" + query.toLowerCase() + "%");
    }


    public static Specification<SongEntity> songWithAnyMatch(String query) {
        return Specification.where(songWithNameContaining(query))
                .or(songWithArtistContaining(query))
                .or(songWithAlbumContaining(query));
    }

    public static Specification<ArtistEntity> artistWithNameContaining(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                query == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + query.toLowerCase() + "%");
    }


    public static Specification<AlbumEntity> albumWithTitleContaining(String query) {
        return (root, criteriaQuery, criteriaBuilder) ->
                query == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + query.toLowerCase() + "%");
    }
}
