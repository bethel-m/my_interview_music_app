package com.example.interview_music.data.artists.extensions

import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.local.model.LocalArtist
import com.example.interview_music.data.artists.repository.remote.model.ArtistNetworkModel

fun ArtistNetworkModel.toLocal(): LocalArtist {
    return LocalArtist(
        id = this.id.toInt(),
        name = this.name,
        image = this.image
    )
}