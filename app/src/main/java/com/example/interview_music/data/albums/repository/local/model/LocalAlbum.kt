package com.example.interview_music.data.albums.repository.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalAlbum(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo("artist_id") val artistId: String,
    @ColumnInfo("artist_name") val artistName: String,
    val image: String,
)
