package com.example.interview_music.data.artists.repository.remote

import com.example.interview_music.data.artists.extensions.toDomain
import com.example.interview_music.data.artists.extensions.toLocal
import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.RemoteArtistRepository
import com.example.interview_music.data.artists.repository.local.model.LocalArtistDao
import com.example.interview_music.data.network_setup.retrofit.RetrofitNetworkApi
import javax.inject.Inject

class RemoteArtistRepositoryImpl @Inject constructor(
    private val remoteApi: RetrofitNetworkApi,
    private val localArtistDao: LocalArtistDao,
) : RemoteArtistRepository {
    private var offset = 0
    private val limit = 20

    override suspend fun loadArtist(): List<Artist> {
        try {
            val remoteArtist = remoteApi.getArtists(
                limit = limit,
                offset = offset,
            )
            val localArtists = remoteArtist.results.map { artist ->
                artist.toLocal()
            }
            localArtistDao.insertAll(localArtists)
            offset += limit
            val artists = remoteArtist.results.map { artist ->
                artist.toDomain()
            }
            return artists
        } catch (e: Exception) {
            throw e;
        }
    }

    override suspend fun loadMoreArtist(): List<Artist> {
        try {
            val remoteArtist = remoteApi.getArtists(
                limit = limit,
                offset = offset,
            )
            val localArtists = remoteArtist.results.map { artist ->
                artist.toLocal()
            }
            localArtistDao.insertAll(localArtists)
            offset += limit
            val artists = remoteArtist.results.map { artist ->
                artist.toDomain()
            }
            return artists
        } catch (e: Exception) {
            throw e;
        }
    }
}