package com.example.interview_music.data.albums.extensions

import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.data.albums.repository.local.model.LocalAlbum

fun LocalAlbum.toDomain(): Album {
    return Album(
        id = id,
        name = name,
        artistId = artistId,
        artistName = artistName,
        image = image,
    )
}