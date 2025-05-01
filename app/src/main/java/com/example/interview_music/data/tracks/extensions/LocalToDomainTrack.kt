package com.example.interview_music.data.tracks.extensions

import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.local.model.LocalTrack


fun LocalTrack.toDomainTrack(): Track {
    return Track(
        id = this.id,
        name = this.name,
        artistId = this.artistId,
        artistName = this.artistName,
        image = this.image,
        audio = this.audioUrl,
        duration = this.duration,
    )
}