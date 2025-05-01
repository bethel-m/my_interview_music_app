package com.example.interview_music.data.artists.repository.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localArtist: LocalArtist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localArtists: List<LocalArtist>)

    @Query("DELETE FROM localArtist")
    suspend fun deleteAll()

    @Query("SELECT * FROM localArtist WHERE id = :id")
    suspend fun getArtistOnlyById(id: Int): LocalArtist

    @Query("SELECT * FROM localArtist")
    fun getAllArtists(): Flow<List<LocalArtist>>

    @Query("SELECT * FROM localArtist")
    suspend fun fetchAllArtists(): List<LocalArtist>
}