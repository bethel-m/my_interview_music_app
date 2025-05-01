package com.example.interview_music.data.album_tracks.repository.remote.model

import com.google.gson.annotations.SerializedName

// Main response class
data class AlbumsTracksResponse(
    @SerializedName("headers") var headers: Header? = null,
    @SerializedName("results") var results: List<RemoteAlbumWithTracks?>? = null
)

// Class to hold header information
data class Header(
    @SerializedName("status") var status: String? = null,
    @SerializedName("code") var code: Int? = null,
    @SerializedName("error_message") var errorMessage: String? = null,
    @SerializedName("warnings") var warnings: String? = null,
    @SerializedName("results_count") var resultsCount: Int? = null
)

// Class to hold individual result (album) information
data class RemoteAlbumWithTracks(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("artist_name") var artistName: String? = null,
    @SerializedName("artist_id") var artistId: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("tracks") var tracks: List<RemoteAlbumTrack?>? = null
)

// Class to hold track information
data class RemoteAlbumTrack(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("duration") var duration: String? = null,
    @SerializedName("audio") var audio: String? = null,
)
