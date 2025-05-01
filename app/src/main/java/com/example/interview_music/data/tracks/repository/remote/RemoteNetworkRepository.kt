package com.example.interview_music.data.tracks.repository.remote

import com.example.interview_music.data.network_setup.retrofit.RetrofitNetworkApi
import com.example.interview_music.data.tracks.extensions.toDomainTrack
import com.example.interview_music.data.tracks.extensions.toLocal
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.RemoteNetworkRepository
import com.example.interview_music.data.tracks.repository.local.model.LocalTrackDao
import javax.inject.Inject

class RemoteNetworkRepositoryImpl @Inject constructor(
    private val remoteApi: RetrofitNetworkApi,
    private val localTrackDao: LocalTrackDao,
) : RemoteNetworkRepository {
    private var offset = 0
    private val limit = 20
    override suspend fun loadTracks(searchQuery: String?, isFeaturedTracks: Int?): List<Track> {
        try {
            val remoteTracks = remoteApi.getTracks(
                limit = limit,
                featured = isFeaturedTracks,
                offset = offset,
                search = searchQuery
            )
            val localTracks = remoteTracks.results.map { track ->
                track.toLocal()
            }
            localTrackDao.insertAll(localTracks)
            offset += limit
            val tracks = remoteTracks.results.map { track ->
                track.toDomainTrack()
            }
            return tracks
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadMoreTracks(searchQuery: String?, isFeaturedTracks: Int?): List<Track> {
        try {
            val remoteTracks = remoteApi.getTracks(
                limit = limit,
                featured = isFeaturedTracks,
                offset = offset,
                search = searchQuery
            )
            val localTracks = remoteTracks.results.map { track ->
                track.toLocal()
            }
            localTrackDao.insertAll(localTracks)
            offset += limit
            val tracks = remoteTracks.results.map { track ->
                track.toDomainTrack()
            }
            return tracks
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadFeaturedTracks(): List<Track> {
        try {
            val remoteTracks = remoteApi.getTracks(
                limit = limit,
                featured = 1,
            )
            val localTracks = remoteTracks.results.map { track ->
                track.toLocal()
            }
            localTrackDao.insertAll(localTracks)

            val tracks = remoteTracks.results.map { track ->
                track.toDomainTrack()
            }
            return tracks
        } catch (e: Exception) {
            throw e;
        }
    }

}