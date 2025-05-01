package com.example.interview_music.data.albums.extensions

import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.data.albums.repository.remote.model.RemoteAlbum


fun RemoteAlbum.toDomain(): Album {
    return Album(
        id = id.toInt(),
        name = name,
        artistId = artist_id,
        artistName = artist_name,
        image = image,
    )
}