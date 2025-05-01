package com.example.interview_music.data.albums.repository.local.model


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalAlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localAlbum: LocalAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(localAlbums: List<LocalAlbum>)

    @Query("DELETE FROM localAlbum")
    suspend fun deleteAll()

    @Query("SELECT * FROM localAlbum WHERE id = :id")
    suspend fun getAlbumOnlyById(id: Int): LocalAlbum

    @Query("SELECT * FROM localAlbum")
    fun getAllAlbums(): Flow<List<LocalAlbum>>

    @Query("SELECT * FROM localAlbum")
    suspend fun fetchAllAlbums(): List<LocalAlbum>
}