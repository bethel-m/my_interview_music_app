package com.example.interview_music.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.data.albums.repository.LocalAlbumRepository
import com.example.interview_music.data.albums.repository.RemoteAlbumRepository
import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.data.artists.repository.LocalArtistRepository
import com.example.interview_music.data.artists.repository.RemoteArtistRepository
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.LocalTracksRepository
import com.example.interview_music.data.tracks.repository.RemoteNetworkRepository
import com.example.interview_music.ui.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localTrackRepository: LocalTracksRepository,
    private val localAlbumRepository: LocalAlbumRepository,
    private val localArtistRepository: LocalArtistRepository,
    private val remoteAlbumRepository: RemoteAlbumRepository,
    private val remoteArtistRepository: RemoteArtistRepository,
    private val remoteNetworkRepository: RemoteNetworkRepository
) : ViewModel() {

    private val _tracksUiState = MutableStateFlow<UiState<List<Track>>>(UiState.Loading)
    val tracksUiState: StateFlow<UiState<List<Track>>> = _tracksUiState

    private val _featuredTracksUiState = MutableStateFlow<UiState<List<Track>>>(UiState.Loading)
    val featuredTracksUiState: StateFlow<UiState<List<Track>>> = _featuredTracksUiState

    private val _albumsUiState = MutableStateFlow<UiState<List<Album>>>(UiState.Loading)
    val albumsUiState: StateFlow<UiState<List<Album>>> = _albumsUiState

    private val _artistUiState = MutableStateFlow<UiState<List<Artist>>>(UiState.Loading)
    val artistUiState: StateFlow<UiState<List<Artist>>> = _artistUiState

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    private val _showSnackBarError = MutableStateFlow(false)
    val showSnackBarError: StateFlow<Boolean> = _showSnackBarError


    private var currentSearchQuery: String? = null

    init {
        obServeAlbums()
        observeArtists()
        observeTracks()
        loadInitialTracks()
    }

    private fun observeTracks() {
        //Observe tracks
        localTrackRepository.getTracksStream()
            .onStart {
                _tracksUiState.value = UiState.Loading
            }
            .catch { e ->
                _tracksUiState.value =
                    UiState.Error("an error occurred, check your connection and try again")
                _showSnackBarError.value = true
            }
            .onEach { tracks ->
                _tracksUiState.value = UiState.Success(tracks)
            }
            .launchIn(viewModelScope)
    }

    fun obServeAlbums() {

        //Observe albums
        localAlbumRepository.getAlbumStream()
            .onStart {
                _albumsUiState.value = UiState.Loading
            }
            .catch { e ->
                _albumsUiState.value =
                    UiState.Error("")
                _showSnackBarError.value = true
            }
            .onEach { albums ->
                _albumsUiState.value = UiState.Success(albums)
            }
            .launchIn(viewModelScope)


    }

    fun observeArtists() {
        //Observe artists
        localArtistRepository.getArtistStream()
            .onStart {
                _artistUiState.value = UiState.Loading
            }
            .catch { e ->
                _artistUiState.value =
                    UiState.Error("")
                _showSnackBarError.value = true
            }
            .onEach { artist ->
                _artistUiState.value = UiState.Success(artist)
            }
            .launchIn(viewModelScope)
    }

    fun stopShowingSnackBar() {
        _showSnackBarError.value = false
    }

    private fun loadInitialTracks() {
        viewModelScope.launch {
            try {
                _isLoadingMore.value = true
                loadFeaturedTracks()
                remoteAlbumRepository.loadAlbum()
                remoteArtistRepository.loadArtist()
                remoteNetworkRepository.loadTracks()
                _isLoadingMore.value = false
            } catch (e: Exception) {
                _isLoadingMore.value = false
                _tracksUiState.value =
                    UiState.Error("an error occurred, check your connection and try again")

            }

        }
    }

    fun loadFeaturedTracks() {
        viewModelScope.launch {
            try {
                _featuredTracksUiState.value = UiState.Loading
                  val tracks = remoteNetworkRepository.loadFeaturedTracks()
                _featuredTracksUiState.value = UiState.Success(tracks)

            } catch (e: Exception) {
                _isLoadingMore.value = false
                _tracksUiState.value =
                    UiState.Error("an error occurred, check your connection and try again")

            }
        }
    }


    fun loadMoreTracks() {
        viewModelScope.launch {
            try {
                _isLoadingMore.value = true
                remoteNetworkRepository.loadMoreTracks()
                _isLoadingMore.value = false
            } catch (e: Exception) {
                _isLoadingMore.value = false
                _tracksUiState.value =
                    UiState.Error("an error occurred, check your connection and try again")

            }

        }
    }

}


