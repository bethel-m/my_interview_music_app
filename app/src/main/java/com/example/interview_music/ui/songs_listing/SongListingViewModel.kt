package com.example.interview_music.ui.songs_listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview_music.data.album_tracks.repository.RemoteAlbumTracksRepository
import com.example.interview_music.data.artist_tracks.repository.RemoteArtistTracksRepository
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.data.tracks.repository.LocalTracksRepository
import com.example.interview_music.data.tracks.repository.RemoteNetworkRepository
import com.example.interview_music.ui.shared.UiState
import com.example.interview_music.ui.songs_listing.extensions.toTracks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongListingViewModel @Inject constructor(
    private val localTrackRepository: LocalTracksRepository,
    private val remoteTrackRepository: RemoteNetworkRepository,
    private val remoteAlbumTracksRepository: RemoteAlbumTracksRepository,
    private val remoteArtistTracksRepository: RemoteArtistTracksRepository,

    ) : ViewModel() {

    private val _songsListingUiState =
        MutableStateFlow<UiState<SongListingUiState>>(UiState.Loading)
    val songsListingUiState: StateFlow<UiState<SongListingUiState>> = _songsListingUiState

    val _showErrorMessage = MutableStateFlow(false)
    val showErrorMessage: StateFlow<Boolean> = _showErrorMessage

    fun loadAlbumTracks(albumId: Int) {
        viewModelScope.launch {
            _songsListingUiState.value = UiState.Loading
            try {
                val albumTracks = remoteAlbumTracksRepository.loadAlbumTracks(albumId)
                if (albumTracks != null) {
                    val albumWithTracks = albumTracks[0]
                    val tracks = albumWithTracks?.toTracks()
                    val uiState = SongListingUiState(
                        bannerImageUrl = albumWithTracks?.image ?: "",
                        title = albumWithTracks?.name ?: "",
                        subTitle = albumWithTracks?.artistName ?: "",
                        tracks = tracks ?: emptyList()
                    )
                    _songsListingUiState.value = UiState.Success(uiState)
                }
            } catch (e: Exception) {
                _showErrorMessage.value = true
                _songsListingUiState.value = UiState.Error("error")
            }

        }
    }

    fun loadArtistTracks(artistId: Int) {
        viewModelScope.launch {
            _songsListingUiState.value = UiState.Loading
            try {
                val artistTracks = remoteArtistTracksRepository.loadArtistTracks(artistId)
                val artistWithTracks = artistTracks[0]
                val tracks = artistWithTracks.toTracks()
                val uiState = SongListingUiState(
                    bannerImageUrl = artistWithTracks.image,
                    title = artistWithTracks.name,
                    subTitle = "",
                    tracks = tracks,
                )
                _songsListingUiState.value = UiState.Success(uiState)
            } catch (e: Exception) {
                _showErrorMessage.value = true
                _songsListingUiState.value = UiState.Error("error")
            }

        }
    }

    fun stopShowingError() {
        _showErrorMessage.value = false
    }
}

data class SongListingUiState(
    val bannerImageUrl: String = "",
    val title: String = "",
    val subTitle: String = "",
    val tracks: List<Track> = emptyList()
)
