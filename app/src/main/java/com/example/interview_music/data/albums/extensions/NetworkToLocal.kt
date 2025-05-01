package com.example.interview_music.data.albums.extensions

import com.example.interview_music.data.albums.repository.local.model.LocalAlbum
import com.example.interview_music.data.albums.repository.remote.model.RemoteAlbum

fun RemoteAlbum.toLocal(): LocalAlbum {
    return LocalAlbum(
        id = id.toInt(),
        name = name,
        artistId = artist_id,
        artistName = artist_name,
        image = image,
    )
}