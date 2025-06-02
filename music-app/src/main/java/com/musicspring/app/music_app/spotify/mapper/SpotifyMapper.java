package com.musicspring.app.music_app.spotify.mapper;

import com.musicspring.app.music_app.model.dto.AlbumRequest;
import com.musicspring.app.music_app.model.dto.ArtistRequest;
import com.musicspring.app.music_app.model.dto.SongRequest;

import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class SpotifyMapper {

    public AlbumRequest toAlbumRequest(AlbumSimplified spotifyAlbum) {
        if (spotifyAlbum == null) {
            return null;
        }

        // Get image URL from Spotify
        String imageUrl = null;
        Image[] images = spotifyAlbum.getImages();
        if (images != null && images.length > 0) {
            imageUrl = images[0].getUrl();
        }

        return AlbumRequest.builder()
                .spotifyId(spotifyAlbum.getId())
                .title(spotifyAlbum.getName())
                .releaseDate(parseReleaseDate(spotifyAlbum.getReleaseDate()))
                .imageUrl(imageUrl)
                .build();
    }
    
    public AlbumRequest toAlbumRequest(Album spotifyAlbum) {
        if (spotifyAlbum == null) {
            return null;
        }

        // Get image URL from Spotify
        String imageUrl = null;
        Image[] images = spotifyAlbum.getImages();
        if (images != null && images.length > 0) {
            imageUrl = images[0].getUrl();
        }

        return AlbumRequest.builder()
                .spotifyId(spotifyAlbum.getId())
                .title(spotifyAlbum.getName())
                .releaseDate(parseReleaseDate(spotifyAlbum.getReleaseDate()))
                .imageUrl(imageUrl)
                .build();
    }


    public ArtistRequest toArtistRequest(Artist spotifyArtist) {
        if (spotifyArtist == null) {
            return null;
        }

        // Get image URL from Spotify
        String imageUrl = null;
        Image[] images = spotifyArtist.getImages();
        if (images != null && images.length > 0) {
            imageUrl = images[0].getUrl();
        }

        return ArtistRequest.builder()
                .name(spotifyArtist.getName())
                .followers(spotifyArtist.getFollowers().getTotal())
                .spotifyId(spotifyArtist.getId())
                .imageUrl(imageUrl)
                .build();
    }
    
    public SongRequest toSongRequest(Track spotifyTrack) {
        if (spotifyTrack == null) {
            return null;
        }
        
        SongRequest songRequest = new SongRequest();
        songRequest.setSpotifyId(spotifyTrack.getId());
        songRequest.setName(spotifyTrack.getName());
        songRequest.setDurationMs(spotifyTrack.getDurationMs());
        
        if (spotifyTrack.getExternalUrls() != null) {
            songRequest.setSpotifyLink(spotifyTrack.getExternalUrls().get("spotify"));
        }
        
        songRequest.setPreviewUrl(spotifyTrack.getPreviewUrl());
        
        ArtistSimplified[] artists = spotifyTrack.getArtists();
        if (artists != null && artists.length > 0) {
            songRequest.setArtistName(artists[0].getName());
        } else {
            songRequest.setArtistName("Unknown artist");
        }
        
        AlbumSimplified album = spotifyTrack.getAlbum();
        if (album != null) {
            songRequest.setAlbumName(album.getName());
            
            Image[] images = album.getImages();
            if (images != null && images.length > 0) {
                songRequest.setImageUrl(images[0].getUrl());
            }
            
            LocalDate releaseDate = parseReleaseDate(album.getReleaseDate());
            if (releaseDate != null) {
                songRequest.setReleaseDate(Date.valueOf(releaseDate));
            }
        }
        
        return songRequest;
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