package com.example.interview_music.data.albums.repository.local

import com.example.interview_music.data.albums.extensions.toDomain
import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.data.albums.repository.LocalAlbumRepository
import com.example.interview_music.data.albums.repository.local.model.LocalAlbumDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalAlbumRepositoryImpl @Inject constructor(
    private val localAlbumsDao: LocalAlbumDao,
) : LocalAlbumRepository {
    override fun getAlbumStream(): Flow<List<Album>> {
        val localAlbums = localAlbumsDao.getAllAlbums()
        val albums = localAlbums.map { album ->
            album.map { it.toDomain() }
        }
        return albums;
    }

    override suspend fun getAlbumById(id: Int): Result<Album> {
        try {
            val album = localAlbumsDao.getAlbumOnlyById(id)
            return Result.success(album.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)

        }
    }

    override suspend fun fetchAllAlbum(): Result<List<Album>> {
        try {
            val albums = localAlbumsDao.fetchAllAlbums()
            return Result.success(albums.map { it.toDomain() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}