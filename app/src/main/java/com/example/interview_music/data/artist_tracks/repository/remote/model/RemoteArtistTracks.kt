package com.example.interview_music.data.artist_tracks.repository.remote.model

data class ArtistTracksResponse(
    val headers: Headers,
    val results: List<RemoteArtistWithTracks>
)

data class RemoteArtistWithTracks(
    val id: String,
    val name: String,
    val image: String,
    val tracks: List<RemoteArtistTrack>
)

data class RemoteArtistTrack(
    val id: String,
    val name: String,
    val duration: String,
    val releasedate: String,
    val audio: String,
    val album_id: String,
    val album_name: String,
    val album_image: String,
    val image: String,
    val audiodownload: String,
    val audiodownload_allowed: Boolean,
    val license_ccurl: String
)

data class Headers(
    val status: String,
    val code: Int,
    val error_message: String?,
    val warnings: String?,
    val results_count: Int
)
