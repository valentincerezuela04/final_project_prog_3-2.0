package com.musicspring.app.music_app.spotify.mapper;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.song.model.entity.SongEntity;

import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.time.LocalDate;

@Component
public class SpotifyMapper {

    public AlbumEntity toAlbumEntity(AlbumSimplified spotifyAlbum) {
        if (spotifyAlbum == null) {
            return null;
        }

        AlbumEntity album = new AlbumEntity();
        album.setSpotifyId(spotifyAlbum.getId());
        album.setTitle(spotifyAlbum.getName());
        album.setReleaseDate(parseReleaseDate(spotifyAlbum.getReleaseDate()));
        album.setActive(true);

        return album;
    }

    public AlbumEntity toAlbumEntity(Album spotifyAlbum) {
        if (spotifyAlbum == null) {
            return null;
        }

        AlbumEntity album = new AlbumEntity();
        album.setSpotifyId(spotifyAlbum.getId());
        album.setTitle(spotifyAlbum.getName());
        album.setReleaseDate(parseReleaseDate(spotifyAlbum.getReleaseDate()));
        album.setActive(true);

        return album;
    }

    public ArtistEntity toArtistEntity(ArtistSimplified spotifyArtist) {
        if (spotifyArtist == null) {
            return null;
        }

        return ArtistEntity.builder()
                .name(spotifyArtist.getName())
                .active(true)
                .build();
    }

    public ArtistEntity toArtistEntity(Artist spotifyArtist) {
        if (spotifyArtist == null) {
            return null;
        }


        return ArtistEntity.builder()
                .name(spotifyArtist.getName())
                .followers(spotifyArtist.getFollowers().getTotal())
                .active(true)
                .build();
    }
    public SongEntity toSongEntity(Track spotifyTrack) {
        if (spotifyTrack == null) {
            return null;
        }

        SongEntity song = new SongEntity();
        song.setSpotifyId(spotifyTrack.getId());
        song.setName(spotifyTrack.getName());
        song.setDurationMs(spotifyTrack.getDurationMs());
        song.setActive(true);

        if (spotifyTrack.getExternalUrls() != null) {
            song.setSpotifyLink(spotifyTrack.getExternalUrls().get("spotify"));
        }

        song.setPreviewUrl(spotifyTrack.getPreviewUrl());

        ArtistSimplified[] artists = spotifyTrack.getArtists();
        if (artists != null && artists.length > 0) {
            song.setArtistName(artists[0].getName());
        } else {
            song.setArtistName("Unknown artist");
        }

        AlbumSimplified album = spotifyTrack.getAlbum();
        if (album != null) {
            song.setAlbumName(album.getName());

            Image[] images = album.getImages();
            if (images != null && images.length > 0) {
                song.setImageUrl(images[0].getUrl());
            }

            LocalDate releaseDate = parseReleaseDate(album.getReleaseDate());
            if (releaseDate != null) {
                song.setReleaseDate(java.sql.Date.valueOf(releaseDate));
            }
        }

        return song;
    }

    private LocalDate parseReleaseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        if (dateStr.length() == 4) {
            return LocalDate.of(Integer.parseInt(dateStr), 1, 1);
        } else if (dateStr.length() == 7) {
            return LocalDate.parse(dateStr + "-01");
        } else {
            return LocalDate.parse(dateStr);
        }
    }
}