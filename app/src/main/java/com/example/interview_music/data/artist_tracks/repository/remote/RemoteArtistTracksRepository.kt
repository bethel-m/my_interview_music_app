package com.example.interview_music.data.artist_tracks.repository.remote

import com.example.interview_music.data.artist_tracks.extensions.toDomain
import com.example.interview_music.data.artist_tracks.models.ArtistWithTracks
import com.example.interview_music.data.artist_tracks.repository.RemoteArtistTracksRepository
import com.example.interview_music.data.network_setup.retrofit.RetrofitNetworkApi
import javax.inject.Inject

class RemoteArtistTracksRepositoryImpl @Inject constructor(
    private val remoteApi: RetrofitNetworkApi,
) : RemoteArtistTracksRepository {
    private val limit = 20
    override suspend fun loadArtistTracks(id: Int): List<ArtistWithTracks> {
        try {
            val remoteArtistTracks = remoteApi.getArtistsTracks(
                limit = limit,
                id = id
            )
            val artistsTracks = remoteArtistTracks.results.map { artistsTrack ->
                artistsTrack.toDomain()
            }
            return artistsTracks
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadMoreArtistTracks(id: Int): List<ArtistWithTracks> {
        try {
            val remoteArtistTracks = remoteApi.getArtistsTracks(
                limit = limit,
                id = id
            )
            val artistsTracks = remoteArtistTracks.results.map { artistsTrack ->
                artistsTrack.toDomain()
            }
            return artistsTracks
        } catch (e: Exception) {
            throw e;
        }
    }
}