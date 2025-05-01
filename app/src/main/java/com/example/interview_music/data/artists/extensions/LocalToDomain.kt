package com.example.interview_music.data.artists.extensions

import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.local.model.LocalArtist

fun LocalArtist.toDomain(): Artist{
    return Artist(
        id = this.id,
        name = this.name,
        image = this.image
    )
}