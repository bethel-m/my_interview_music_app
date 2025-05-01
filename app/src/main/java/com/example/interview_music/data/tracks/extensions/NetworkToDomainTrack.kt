package com.example.interview_music.data.tracks.extensions

import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.remote.model.NetworkTrack

fun NetworkTrack.toDomainTrack(): Track {
    return Track(
        id = this.id.toInt(),
        name = this.name,
        artistId = this.artistId,
        artistName = this.artistName,
        image = this.image,
        audio = this.audioUrl,
        duration = this.duration,
    )
}
