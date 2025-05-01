package com.example.interview_music.data.tracks.repository.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalTrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localTrack: LocalTrack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localTracks: List<LocalTrack>)

    @Query("DELETE FROM localtrack")
    suspend fun deleteAll()

    @Query("SELECT * FROM localtrack WHERE id = :id")
    suspend fun getTrackOnlyById(id: Int): LocalTrack

    @Query("SELECT * FROM localtrack")
    fun getAllTracks(): Flow<List<LocalTrack>>

    @Query("SELECT * FROM localtrack")
    suspend fun fetchAllTracks(): List<LocalTrack>

}