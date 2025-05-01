package com.example.interview_music.data.tracks.repository.local

import com.example.interview_music.data.tracks.extensions.toDomainTrack
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.LocalTracksRepository
import com.example.interview_music.data.tracks.repository.local.model.LocalTrackDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNetworkRepositoryImpl @Inject constructor(
    private val localTrackDao: LocalTrackDao
) : LocalTracksRepository {
    override fun getTracksStream(): Flow<List<Track>> {
        val localTracks = localTrackDao.getAllTracks()
        val tracks = localTracks.map { track ->
            track.map { it.toDomainTrack() }
        }
        return tracks;
    }

    override suspend fun getTrackById(id: Int): Result<Track> {
        try {
            val track = localTrackDao.getTrackOnlyById(id)
            return Result.success(track.toDomainTrack())
        } catch (e: Exception) {
            return Result.failure(e)

        }
    }

    override suspend fun fetchAllTracks(): Result<List<Track>> {
        try {
            val tracks = localTrackDao.fetchAllTracks()
            return Result.success(tracks.map { it.toDomainTrack() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun fetchFeaturedTracks(): Result<List<Track>> {
        TODO("Not yet implemented")
    }


}