package com.example.interview_music.data.tracks.di

import com.example.interview_music.data.tracks.repository.LocalTracksRepository
import com.example.interview_music.data.tracks.repository.RemoteNetworkRepository
import com.example.interview_music.data.tracks.repository.local.LocalNetworkRepositoryImpl
import com.example.interview_music.data.tracks.repository.remote.RemoteNetworkRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TracksModule {

    @Binds
    abstract fun remoteRepository(
        remoteNetworkRepository: RemoteNetworkRepositoryImpl
    ): RemoteNetworkRepository

    @Binds
    abstract fun localRepository(
        localNetworkRepository: LocalNetworkRepositoryImpl
    ): LocalTracksRepository
}