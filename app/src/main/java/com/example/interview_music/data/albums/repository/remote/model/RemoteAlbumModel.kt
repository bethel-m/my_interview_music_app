package com.example.interview_music.data.albums.repository.remote.model

data class AlbumsListResponse(
    val headers: Headers,
    val results: List<RemoteAlbum>
)

data class RemoteAlbum(
    val id: String,
    val name: String,
    val releasedate: String,
    val artist_id: String,
    val artist_name: String,
    val image: String,
    val zip: String,
    val shorturl: String,
    val shareurl: String,
    val zip_allowed: Boolean
)

data class Headers(
    val status: String,
    val code: Int,
    val error_message: String?,
    val warnings: String?,
    val results_count: Int,
    val next: String?
)
