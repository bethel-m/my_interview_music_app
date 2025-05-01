package com.example.interview_music.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.RemoteNetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val remoteTrackRepository: RemoteNetworkRepository,
) : ViewModel() {
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore
    private val _searchResults = MutableStateFlow<List<Track>>(emptyList())
    val searchResults: StateFlow<List<Track>> = _searchResults

    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError

    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }

    fun search(text: String) {
        viewModelScope.launch {
            try {
                _isLoadingMore.value = true
                val tracks = remoteTrackRepository.loadTracks(
                    searchQuery = searchQuery
                )
                _searchResults.value = tracks
                _isLoadingMore.value = false
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }

    }

    fun stopShowingError() {
        _showError.value = false
    }

}