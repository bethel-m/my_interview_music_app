package com.example.interview_music.data.album_tracks.repository.remote

import com.example.interview_music.data.album_tracks.extensions.toDomain
import com.example.interview_music.data.album_tracks.models.AlbumWithTracks
import com.example.interview_music.data.album_tracks.repository.RemoteAlbumTracksRepository
import com.example.interview_music.data.network_setup.retrofit.RetrofitNetworkApi
import javax.inject.Inject

class RemoteAlbumTracksRepositoryImpl @Inject constructor(
    private val remoteApi: RetrofitNetworkApi
) : RemoteAlbumTracksRepository {
    private val limit = 20
    override suspend fun loadAlbumTracks(id: Int): List<AlbumWithTracks?>? {
        try {
            val remoteAlbumTracks = remoteApi.getAlbumTracks(
                limit = limit,
                id = id
            )
            val albumsTracks = remoteAlbumTracks.results?.map { albumsTrack ->
                albumsTrack?.toDomain()
            }
            return albumsTracks
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadMoreAlbumTracks(id: Int): List<AlbumWithTracks?>? {
        try {
            val remoteAlbumTracks = remoteApi.getAlbumTracks(
                limit = limit,
                id = id
            )
            val albumsTracks = remoteAlbumTracks.results?.map { albumsTrack ->
                albumsTrack?.toDomain()
            }
            return albumsTracks
        } catch (e: Exception) {
            throw e;
        }
    }

}