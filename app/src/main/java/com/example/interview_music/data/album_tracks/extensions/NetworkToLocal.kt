package com.example.interview_music.data.album_tracks.extensions

import com.example.interview_music.data.album_tracks.models.AlbumTrack
import com.example.interview_music.data.album_tracks.models.AlbumWithTracks
import com.example.interview_music.data.album_tracks.repository.remote.model.RemoteAlbumTrack
import com.example.interview_music.data.album_tracks.repository.remote.model.RemoteAlbumWithTracks


fun RemoteAlbumWithTracks.toDomain(): AlbumWithTracks {
    return AlbumWithTracks(
        id = id,
        name = name,
        artistName = artistName,
        artistId = artistId,
        image = image,
        tracks = tracks?.map { it?.toDomain() }
    )
}

fun RemoteAlbumTrack.toDomain(): AlbumTrack {
    return AlbumTrack(
        id = id?.toIntOrNull(),
        name = name,
        duration = duration?.toIntOrNull(),
        audio = audio,
    )
}