package com.example.interview_music.data.artists.repository.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalArtist(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String
)

