package com.example.interview_music.ui.home


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interview_music.R
import com.example.interview_music.ui.home.components.HomeCircularCardsListing
import com.example.interview_music.ui.home.components.HomeHorizontalCarouselListing
import com.example.interview_music.ui.home.components.HomeMusicTileListing
import com.example.interview_music.ui.home.components.HomeRectangularCardsListing
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.shared.UiLoading
import com.example.interview_music.ui.shared.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onAlbumNavigationToListingScreen: (id: Int) -> Unit = {},
    onArtistNavigationToListingScreen: (id: Int) -> Unit = {},
    onNavigateToMusicPlayerScreen: (id: Int) -> Unit = { },
    onStreamToMusicPlayerScreen: () -> Unit = { },
    viewModel: HomeViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
   showErrorSnackBar: () -> Unit = {},

    ) {
    val tracksUiState by viewModel.tracksUiState.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val showSnackBarError by viewModel.showSnackBarError.collectAsStateWithLifecycle()
    val albumsUiState by viewModel.albumsUiState.collectAsStateWithLifecycle()
    val artistUiState by viewModel.artistUiState.collectAsStateWithLifecycle()
    val featuredTracksUiState by viewModel.featuredTracksUiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
    )
    {
        item {
            if ((isLoadingMore)) {
                UiLoading()
            }

        }
        item {
            if (featuredTracksUiState is UiState.Success) {
                val tracks = (featuredTracksUiState as UiState.Success).data
                HomeHorizontalCarouselListing(
                    sectionTitle = stringResource(R.string.featured),
                    tracks = tracks,
                    onClick = { id ->
                        musicPlayerViewModel.addStreamTracks(tracks)
                        onStreamToMusicPlayerScreen()
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            if (tracksUiState is UiState.Success) {
                val tracks = (tracksUiState as UiState.Success).data
                HomeMusicTileListing(
                    tracks = tracks,
                    sectionTitle = stringResource(R.string.random),
                    onMorePressed = {},
                    onMusicTilePressed = { id ->
                        onNavigateToMusicPlayerScreen(
                            id,
                        )
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            if (albumsUiState is UiState.Success) {
                val albums = (albumsUiState as UiState.Success).data
                HomeRectangularCardsListing(
                    sectionTitle = stringResource(R.string.albums),
                    albums = albums,
                    onAlbumPressed = { id -> onAlbumNavigationToListingScreen(id) }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            if (artistUiState is UiState.Success) {
                val artists = (artistUiState as UiState.Success).data
                HomeCircularCardsListing(
                    sectionTitle = stringResource(R.string.artists),
                    artists = artists,
                    onArtistPressed = { id -> onArtistNavigationToListingScreen(id) }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(64.dp))
        }

    }
    if (showSnackBarError) {
        showErrorSnackBar()
        viewModel.stopShowingSnackBar()
    }
}


