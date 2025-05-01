package com.example.interview_music.data.album_tracks.di

import com.example.interview_music.data.album_tracks.repository.RemoteAlbumTracksRepository
import com.example.interview_music.data.album_tracks.repository.remote.RemoteAlbumTracksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumTracksModule {

    @Binds
    abstract fun remoteRepository(
        remoteAlbumTracksRepository: RemoteAlbumTracksRepositoryImpl
    ): RemoteAlbumTracksRepository

}