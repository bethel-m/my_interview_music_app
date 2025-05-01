package com.example.interview_music.data.artists.extensions

import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.remote.model.ArtistNetworkModel

fun ArtistNetworkModel.toDomain(): Artist {
    return Artist(
        id = this.id.toIntOrNull() ?: 0,
        name = this.name,
        image = this.image
    )
}