package com.example.interview_music.data.album_tracks.models

data class AlbumWithTracks(
    val id: String? = null,
    val name: String? = null,
    val artistName: String? = null,
    val artistId: String? = null,
    val image: String? = null,
    val tracks: List<AlbumTrack?>? = null
)

// Class to hold track information
data class AlbumTrack(
    val id: Int? = null,
    val name: String? = null,
    val duration: Int? = null,
    val audio: String? = null,
)