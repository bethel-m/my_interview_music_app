package com.example.interview_music.data.tracks.models

data class Track(
    val id: Int,
    val name: String,
    val artistId: String,
    val artistName: String,
    val image: String,
    val audio: String,
    val duration: Int,
)
