package com.example.interview_music.data.artists.repository.local

import com.example.interview_music.data.artists.extensions.toDomain
import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.LocalArtistRepository
import com.example.interview_music.data.artists.repository.local.model.LocalArtistDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalArtistRepositoryImpl @Inject constructor(
    private val localArtistsDao: LocalArtistDao,
) : LocalArtistRepository {
    override fun getArtistStream(): Flow<List<Artist>> {
        val localArtists = localArtistsDao.getAllArtists()
        val artists = localArtists.map { artist ->
            artist.map { it.toDomain() }
        }
        return artists;
    }

    override suspend fun getArtistById(id: Int): Result<Artist> {
        try {
            val artist = localArtistsDao.getArtistOnlyById(id)
            return Result.success(artist.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)

        }
    }

    override suspend fun fetchAllArtist(): Result<List<Artist>> {
        try {
            val artists = localArtistsDao.fetchAllArtists()
            return Result.success(artists.map { it.toDomain() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}