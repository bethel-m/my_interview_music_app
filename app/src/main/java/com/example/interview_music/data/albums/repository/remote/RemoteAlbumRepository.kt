package com.example.interview_music.data.albums.repository.remote

import com.example.interview_music.data.albums.extensions.toDomain
import com.example.interview_music.data.albums.extensions.toLocal
import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.data.albums.repository.RemoteAlbumRepository
import com.example.interview_music.data.albums.repository.local.model.LocalAlbumDao
import com.example.interview_music.data.network_setup.retrofit.RetrofitNetworkApi
import javax.inject.Inject

class RemoteAlbumRepositoryImpl @Inject constructor(
    private val remoteApi: RetrofitNetworkApi,
    private val localAlbumDao: LocalAlbumDao,
) : RemoteAlbumRepository {
    private var offset = 0
    private val limit = 20
    override suspend fun loadAlbum(): List<Album> {
        try {
            val remoteAlbum = remoteApi.getAlbums(
                limit = limit,
                offset = offset,
            )
            val localAlbums = remoteAlbum.results.map { album ->
                album.toLocal()
            }
            localAlbumDao.insertAll(localAlbums)
            offset += limit
            val albums = remoteAlbum.results.map { album ->
                album.toDomain()
            }
            return albums
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadMoreAlbum(): List<Album> {
        try {
            val remoteAlbum = remoteApi.getAlbums(
                limit = limit,
                offset = offset,
            )
            val localAlbums = remoteAlbum.results.map { album ->
                album.toLocal()
            }
            localAlbumDao.insertAll(localAlbums)
            offset += limit
            val albums = remoteAlbum.results.map { album ->
                album.toDomain()
            }
            return albums
        } catch (e: Exception) {
            throw e;
        }
    }
}