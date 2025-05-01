package com.example.interview_music.data.database_setup.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interview_music.data.albums.repository.local.model.LocalAlbum
import com.example.interview_music.data.albums.repository.local.model.LocalAlbumDao
import com.example.interview_music.data.artists.repository.local.model.LocalArtist
import com.example.interview_music.data.artists.repository.local.model.LocalArtistDao
import com.example.interview_music.data.tracks.repository.local.model.LocalTrack
import com.example.interview_music.data.tracks.repository.local.model.LocalTrackDao


@Database(
    entities = [LocalTrack::class, LocalAlbum::class, LocalArtist::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getLocalTrackDao(): LocalTrackDao
    abstract fun getLocalAlbumDao(): LocalAlbumDao
    abstract fun getLocalArtistDao(): LocalArtistDao
}



