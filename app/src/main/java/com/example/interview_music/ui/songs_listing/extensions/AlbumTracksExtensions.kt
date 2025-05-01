package com.example.interview_music.ui.songs_listing.extensions

import com.example.interview_music.data.album_tracks.models.AlbumWithTracks
import com.example.interview_music.data.tracks.models.Track

fun AlbumWithTracks.toTracks(): List<Track> {
    var allTracks = mutableListOf<Track>()
    if (tracks != null) {
        val tracks = tracks.map { albumTrack ->
            if (albumTrack != null) {
                val newTrack = Track(
                    id = albumTrack.id ?: -1,
                    name = albumTrack.name ?: "",
                    artistId = artistId ?: "",
                    artistName = artistName ?: "",
                    image = image ?: "",
                    audio = albumTrack.audio ?: "",
                    duration = albumTrack.duration ?: 0,
                )
                allTracks.add(newTrack)
            }
        }
    }
    return allTracks
}