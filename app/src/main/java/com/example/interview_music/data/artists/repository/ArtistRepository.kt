package com.example.interview_music.data.artists.repository

import com.example.interview_music.data.artists.models.Artist
import kotlinx.coroutines.flow.Flow

interface LocalArtistRepository {
    fun getArtistStream(): Flow<List<Artist>>
    suspend fun getArtistById(id: Int): Result<Artist>
    suspend fun fetchAllArtist(): Result<List<Artist>>
}

interface RemoteArtistRepository {
    suspend fun loadArtist(): List<Artist>
    suspend fun loadMoreArtist(): List<Artist>
}
