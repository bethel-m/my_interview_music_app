package com.example.interview_music.data.album_tracks.repository

import com.example.interview_music.data.album_tracks.models.AlbumWithTracks

interface RemoteAlbumTracksRepository {
    suspend fun loadAlbumTracks(id: Int): List<AlbumWithTracks?>?
    suspend fun loadMoreAlbumTracks(id: Int): List<AlbumWithTracks?>?
}
