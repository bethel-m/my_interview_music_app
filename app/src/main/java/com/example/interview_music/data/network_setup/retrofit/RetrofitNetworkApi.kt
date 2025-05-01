package com.example.interview_music.data.network_setup.retrofit

import com.example.interview_music.data.album_tracks.repository.remote.model.AlbumsTracksResponse
import com.example.interview_music.data.albums.repository.remote.model.AlbumsListResponse
import com.example.interview_music.data.artist_tracks.repository.remote.model.ArtistTracksResponse
import com.example.interview_music.data.artists.repository.remote.model.ArtistsListResponse
import com.example.interview_music.data.network_setup.APIKEY
import com.example.interview_music.data.tracks.repository.remote.model.NetworkTrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetworkApi {
    @GET("tracks/")
    suspend fun getTracks(
        @Query("client_id") clientId: String = APIKEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 1,
        @Query("featured") featured: Int? = null,
        @Query("search") search: String? = null,
    ): NetworkTrackResponse

    @GET("artists/")
    suspend fun getArtists(
        @Query("client_id") clientId: String = APIKEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 1,
    ): ArtistsListResponse

    @GET("artists/tracks/")
    suspend fun getArtistsTracks(
        @Query("client_id") clientId: String = APIKEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("id") id: Int = 1,
    ): ArtistTracksResponse

    @GET("albums/")
    suspend fun getAlbums(
        @Query("client_id") clientId: String = APIKEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 1,
    ): AlbumsListResponse

    @GET("albums/tracks/")
    suspend fun getAlbumTracks(
        @Query("client_id") clientId: String = APIKEY,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("id") id: Int = 1,
    ): AlbumsTracksResponse
}
