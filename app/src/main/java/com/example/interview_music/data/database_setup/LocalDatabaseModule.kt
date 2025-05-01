package com.example.interview_music.data.database_setup

import android.content.Context
import androidx.room.Room
import com.example.interview_music.data.albums.repository.local.model.LocalAlbumDao
import com.example.interview_music.data.artists.repository.local.model.LocalArtistDao
import com.example.interview_music.data.database_setup.database.LocalDatabase
import com.example.interview_music.data.tracks.repository.local.model.LocalTrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "music_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideLocalTrackDao(localDatabase: LocalDatabase): LocalTrackDao {
        return localDatabase.getLocalTrackDao()
    }

    @Provides
    fun provideLocalAlbumDao(localDatabase: LocalDatabase): LocalAlbumDao {
        return localDatabase.getLocalAlbumDao()
    }

    @Provides
    fun provideLocalArtistDao(localDatabase: LocalDatabase): LocalArtistDao {
        return localDatabase.getLocalArtistDao()
    }

}