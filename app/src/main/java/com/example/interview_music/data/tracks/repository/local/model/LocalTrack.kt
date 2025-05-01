package com.example.interview_music.data.tracks.repository.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalTrack(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo("artist_name") val artistName: String,
    @ColumnInfo("artist_id") val artistId: String,
    @ColumnInfo("album_name") val albumName: String,
    @ColumnInfo("releasedate") val releaseDate: String,
    val duration: Int,
    val audioUrl: String,
    val image: String
)

