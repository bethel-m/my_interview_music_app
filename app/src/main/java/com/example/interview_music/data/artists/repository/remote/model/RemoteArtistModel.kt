package com.example.interview_music.data.artists.repository.remote.model

data class ArtistsListResponse(
    val headers: ArtistResponseHeaders,
    val results: List<ArtistNetworkModel>
)

data class ArtistResponseHeaders(
    val status: String,
    val code: Int,
    val error_message: String?,
    val warnings: String?,
    val results_count: Int,
    val next: String?
)

data class ArtistNetworkModel(
    val id: String,                      // Artist ID
    val name: String,                   // Artist Name
    val image: String                   // Artist Picture URL
)
