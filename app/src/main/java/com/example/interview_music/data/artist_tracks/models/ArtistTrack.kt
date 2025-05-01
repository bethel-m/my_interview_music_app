package com.example.interview_music.data.artist_tracks.models

data class ArtistWithTracks (
    val id: String,
    val name: String,
    val image: String,
    val tracks: List<ArtistTrack>
)

data class ArtistTrack(
    val id: Int,
    val name: String,
    val duration: Int,
    val audio: String,
    val image: String,
)