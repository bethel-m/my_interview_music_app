package com.example.interview_music.ui.songs_listing.extensions

import com.example.interview_music.data.artist_tracks.models.ArtistWithTracks
import com.example.interview_music.data.tracks.models.Track

fun ArtistWithTracks.toTracks(): List<Track> {
    val allTracks = mutableListOf<Track>()
    tracks.map { track ->
        val newTrack = Track(
            id = track.id,
            name = track.name,
            artistId = id,
            artistName = name,
            image = track.image,
            audio = track.audio,
            duration = track.duration,
        )
        allTracks.add(newTrack)
    }
    return allTracks
}