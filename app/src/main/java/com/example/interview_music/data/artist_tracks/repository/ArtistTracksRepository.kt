package com.example.interview_music.data.artist_tracks.repository

import com.example.interview_music.data.artist_tracks.models.ArtistWithTracks

interface RemoteArtistTracksRepository {
    suspend fun loadArtistTracks(id: Int): List<ArtistWithTracks>
    suspend fun loadMoreArtistTracks(id: Int): List<ArtistWithTracks>
}
