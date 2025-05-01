package com.example.interview_music.data.artists.di

import com.example.interview_music.data.artists.repository.LocalArtistRepository
import com.example.interview_music.data.artists.repository.RemoteArtistRepository
import com.example.interview_music.data.artists.repository.local.LocalArtistRepositoryImpl
import com.example.interview_music.data.artists.repository.remote.RemoteArtistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArtistModule {

    @Binds
    abstract fun remoteRepository(
        remoteArtistRepository: RemoteArtistRepositoryImpl
    ): RemoteArtistRepository

    @Binds
    abstract fun localRepository(
        localArtistRepository: LocalArtistRepositoryImpl
    ): LocalArtistRepository
}