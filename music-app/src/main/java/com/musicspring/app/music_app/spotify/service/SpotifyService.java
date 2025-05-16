package com.musicspring.app.music_app.spotify.service;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongEntity;
import com.musicspring.app.music_app.artist.model.entities.ArtistXSongId;
import com.musicspring.app.music_app.artist.repository.ArtistXSongRepository;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.spotify.config.SpotifyConfig;
import com.musicspring.app.music_app.spotify.mapper.SpotifyMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// We could create personalized exceptions.
public class SpotifyService {

    private final SpotifyApi spotifyApi;
    private final SpotifyConfig spotifyConfig;
    private final SpotifyMapper spotifyMapper;
    private final ArtistXSongRepository artistXSongRepository;

    @Value("${spotify.default.limit:20}")
    private int defaultLimit;

    private void checkTokenExpiration() {
        if (LocalDateTime.now().isAfter(spotifyConfig.getTokenExpiration())) {
            spotifyConfig.refreshToken(spotifyApi);
        }
    }

    public List<AlbumEntity> searchAlbums(String query, int limit, int offset) {
        checkTokenExpiration();

        try {
            SearchAlbumsRequest request = spotifyApi.searchAlbums(query)
                    .limit(limit)
                    .offset(offset)
                    .build();

            Paging<AlbumSimplified> results = request.execute();

            return Arrays.stream(results.getItems())
                    .map(spotifyMapper::toAlbumEntity)
                    .collect(Collectors.toList());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error buscando álbumes: " + e.getMessage(), e);
        }
    }

    public long getTotalAlbumsCount(String query) {
        checkTokenExpiration();

        try {
            SearchAlbumsRequest countRequest = spotifyApi.searchAlbums(query)
                    .limit(1)
                    .offset(0)
                    .build();

            Paging<AlbumSimplified> countResults = countRequest.execute();
            return countResults.getTotal();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<ArtistEntity> searchArtists(String query, int limit, int offset) {
        checkTokenExpiration();

        try {
            SearchArtistsRequest request = spotifyApi.searchArtists(query)
                    .limit(limit)
                    .offset(offset)
                    .build();

            Paging<Artist> results = request.execute();

            return Arrays.stream(results.getItems())
                    .map(spotifyMapper::toArtistEntity)
                    .collect(Collectors.toList());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error while searching artists: " + e.getMessage(), e);
        }
    }

    public long getTotalArtistsCount(String query) {
        checkTokenExpiration();

        try {
            SearchArtistsRequest countRequest = spotifyApi.searchArtists(query)
                    .limit(1)
                    .offset(0)
                    .build();

            Paging<Artist> countResults = countRequest.execute();
            return countResults.getTotal();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<SongEntity> searchSongs(String query, int limit, int offset) {
        checkTokenExpiration();

        try {
            SearchTracksRequest request = spotifyApi.searchTracks(query)
                    .limit(limit)
                    .offset(offset)
                    .build();

            Paging<Track> results = request.execute();

            return Arrays.stream(results.getItems())
                    .map(spotifyMapper::toSongEntity)
                    .collect(Collectors.toList());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error while searching songs: " + e.getMessage(), e);
        }
    }

    public long getTotalSongsCount(String query) {
        checkTokenExpiration();

        try {
            SearchTracksRequest countRequest = spotifyApi.searchTracks(query)
                    .limit(1)
                    .offset(0)
                    .build();

            Paging<Track> countResults = countRequest.execute();
            return countResults.getTotal();
        } catch (Exception e) {
            return 0;
        }
    }

    public AlbumEntity getAlbum(String albumId) {
        checkTokenExpiration();

        try {
            GetAlbumRequest request = spotifyApi.getAlbum(albumId).build();
            se.michaelthelin.spotify.model_objects.specification.Album spotifyAlbum = request.execute();

            return spotifyMapper.toAlbumEntity(spotifyAlbum);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error while obtaining album: " + e.getMessage(), e);
        }
    }

    public ArtistEntity getArtist(String artistId) {
        checkTokenExpiration();

        try {
            GetArtistRequest request = spotifyApi.getArtist(artistId).build();
            se.michaelthelin.spotify.model_objects.specification.Artist spotifyArtist = request.execute();

            return spotifyMapper.toArtistEntity(spotifyArtist);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error while obtaining artist: " + e.getMessage(), e);
        }
    }

    public SongEntity getSong(String trackId) {
        checkTokenExpiration();

        try {
            GetTrackRequest request = spotifyApi.getTrack(trackId).build();
            Track spotifyTrack = request.execute();

            return spotifyMapper.toSongEntity(spotifyTrack);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException("Error while obtaining song: " + e.getMessage(), e);
        }
    }
    public void createArtistSongRelationship(ArtistEntity artist, SongEntity song) {
        ArtistXSongId id = new ArtistXSongId(artist.getArtistId(), song.getSongId());

        ArtistXSongEntity relation = ArtistXSongEntity.builder()
                .artistXSongId(id)
                .artist(artist)
                .song(song)
                .build();

        artist.getArtistSongs().add(relation);

        artistXSongRepository.save(relation);
    }
}