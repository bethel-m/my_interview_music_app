package com.example.interview_music.data.tracks.repository.remote.model

import com.google.gson.annotations.SerializedName

data class NetworkTrackResponse(
    @SerializedName("results") val results: List<NetworkTrack>
)

data class NetworkTrack(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("artist_name") val artistName: String,
    @SerializedName("artist_id") val artistId: String,
    @SerializedName("album_name") val albumName: String,
    @SerializedName("releasedate") val releaseDate: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("audio") val audioUrl: String,
    @SerializedName("image") val image: String
)
