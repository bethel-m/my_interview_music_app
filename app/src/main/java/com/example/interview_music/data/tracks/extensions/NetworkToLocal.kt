package com.example.interview_music.data.tracks.extensions

import com.example.interview_music.data.tracks.repository.local.model.LocalTrack
import com.example.interview_music.data.tracks.repository.remote.model.NetworkTrack


fun NetworkTrack.toLocal(): LocalTrack {
    return LocalTrack(
        id = this.id.toInt(),
        name = this.name,
        artistName = this.artistName,
        artistId = this.artistId,
        albumName = this.albumName,
        releaseDate = this.releaseDate,
        duration = this.duration,
        audioUrl = this.audioUrl,
        image = this.image,
    )

}
