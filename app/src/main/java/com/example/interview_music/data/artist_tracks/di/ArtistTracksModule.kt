package com.example.interview_music.data.artist_tracks.di

import com.example.interview_music.data.artist_tracks.repository.RemoteArtistTracksRepository
import com.example.interview_music.data.artist_tracks.repository.remote.RemoteArtistTracksRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ArtistTracksModule {

    @Binds
    abstract fun remoteRepository(
        remoteArtistTracksRepository: RemoteArtistTracksRepositoryImpl
    ): RemoteArtistTracksRepository

}