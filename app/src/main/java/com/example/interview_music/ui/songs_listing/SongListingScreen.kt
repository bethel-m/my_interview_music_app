package com.example.interview_music.ui.songs_listing


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interview_music.R
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.shared.ErrorSnackBar
import com.example.interview_music.ui.shared.InnerAppBar
import com.example.interview_music.ui.shared.MusicTile
import com.example.interview_music.ui.shared.UiLoading
import com.example.interview_music.ui.shared.UiState
import com.example.interview_music.ui.songs_listing.components.SongListingSummary
import com.example.interview_music.ui.songs_listing.navigation.SongsListingRoute
import kotlinx.coroutines.launch


@Composable
fun SongListingScreen(
    songsListingRoute: SongsListingRoute,
    viewModel: SongListingViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToPlayer: (Boolean) -> Unit = {},
) {
    val uiState = viewModel.songsListingUiState.collectAsStateWithLifecycle()
    val showError = viewModel.showErrorMessage.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        if (songsListingRoute.artistId != null) {
            viewModel.loadArtistTracks(songsListingRoute.artistId)
        } else if (songsListingRoute.albumId != null) {
            viewModel.loadAlbumTracks(songsListingRoute.albumId)
        }
    }


    Scaffold(
        topBar = {
            InnerAppBar(
                title = if (uiState.value is UiState.Success) {
                    val title = (uiState.value as UiState.Success).data.title
                    title
                } else {
                    "..."
                },
                onNavigateBack = onNavigateBack
            )

        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ErrorSnackBar(data)
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            item {
                if (uiState.value is UiState.Loading) {
                    UiLoading()
                }
            }
            if (uiState.value is UiState.Success) {
                val data = (uiState.value as UiState.Success).data
                val tracks = data.tracks
                item {
                    SongListingSummary(
                        title = data.title,
                        subtitle = data.subTitle,
                        imageUrl = data.bannerImageUrl
                    )
                }

                items(tracks.size) { index ->
                    MusicTile(
                        track = tracks[index],
                        onPressed = {
                            val streamAllTracks = mutableListOf<Track>()
                            streamAllTracks.addAll(tracks)
                            streamAllTracks[0] = tracks[index]
                            musicPlayerViewModel.addStreamTracks(streamAllTracks)
                            onNavigateToPlayer(true)
                        }
                    )
                }
            }


        }

    }
    val errorText = stringResource(id = R.string.error_loading)
    if (showError.value) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar(errorText)
            }
            viewModel.stopShowingError()
        }
    }
}
