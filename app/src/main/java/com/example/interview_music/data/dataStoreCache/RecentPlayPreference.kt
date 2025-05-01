package com.example.interview_music.data.dataStoreCache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LastPlayedTrack @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val LAST_PLAYED_ID = intPreferencesKey("last_played_id")
    }

    val lastPlayedId: Flow<Int?> = dataStore.data.map{ preferences ->
        preferences[LAST_PLAYED_ID]
    }

    suspend fun saveLastPlayedId(lastPlayedId: Int) {
        dataStore.edit { preferences ->
            preferences[LAST_PLAYED_ID] = lastPlayedId
        }
    }

}