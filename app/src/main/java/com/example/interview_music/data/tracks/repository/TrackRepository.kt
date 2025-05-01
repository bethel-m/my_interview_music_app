package com.example.interview_music.data.tracks.repository

import com.example.interview_music.data.tracks.models.Track
import kotlinx.coroutines.flow.Flow

interface LocalTracksRepository{
    fun getTracksStream(): Flow<List<Track>>
    suspend fun getTrackById(id: Int): Result<Track>
    suspend fun fetchAllTracks(): Result<List<Track>>
    suspend fun fetchFeaturedTracks(): Result<List<Track>>
}

interface RemoteNetworkRepository{
    suspend fun loadTracks(searchQuery: String? = null,isFeaturedTracks: Int? = null,): List<Track>
    suspend fun loadMoreTracks(searchQuery: String? = null,isFeaturedTracks: Int? = null,): List<Track>
    suspend fun loadFeaturedTracks(): List<Track>
}
