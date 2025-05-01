package com.example.interview_music.data.artist_tracks.extensions

import com.example.interview_music.data.artist_tracks.models.ArtistTrack
import com.example.interview_music.data.artist_tracks.models.ArtistWithTracks
import com.example.interview_music.data.artist_tracks.repository.remote.model.RemoteArtistTrack
import com.example.interview_music.data.artist_tracks.repository.remote.model.RemoteArtistWithTracks

fun RemoteArtistWithTracks.toDomain(): ArtistWithTracks {
    return ArtistWithTracks(
        id = id,
        name = name,
        image = image,
        tracks = tracks.map { it.toDomain() }
    )
}

fun RemoteArtistTrack.toDomain(): ArtistTrack {
    return ArtistTrack(
        id = id.toInt(),
        name = name,
        duration = duration.toInt(),
        audio = audio,
        image = image,
    )
}