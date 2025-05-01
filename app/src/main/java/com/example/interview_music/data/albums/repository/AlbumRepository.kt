package com.example.interview_music.data.albums.repository

import com.example.interview_music.data.albums.models.Album
import kotlinx.coroutines.flow.Flow

interface LocalAlbumRepository {
    fun getAlbumStream(): Flow<List<Album>>
    suspend fun getAlbumById(id: Int): Result<Album>
    suspend fun fetchAllAlbum(): Result<List<Album>>
}

interface RemoteAlbumRepository {
    suspend fun loadAlbum(): List<Album>
    suspend fun loadMoreAlbum(): List<Album>
}
