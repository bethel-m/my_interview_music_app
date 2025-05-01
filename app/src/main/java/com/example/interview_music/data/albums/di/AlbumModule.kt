package com.example.interview_music.data.albums.di

import com.example.interview_music.data.albums.repository.LocalAlbumRepository
import com.example.interview_music.data.albums.repository.RemoteAlbumRepository
import com.example.interview_music.data.albums.repository.local.LocalAlbumRepositoryImpl
import com.example.interview_music.data.albums.repository.remote.RemoteAlbumRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumModule {

    @Binds
    abstract fun remoteRepository(
        remoteAlbumRepository: RemoteAlbumRepositoryImpl
    ): RemoteAlbumRepository

    @Binds
    abstract fun localRepository(
        localAlbumRepository: LocalAlbumRepositoryImpl
    ): LocalAlbumRepository
}