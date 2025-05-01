package com.example.interview_music.ui.music_player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interview_music.R
import com.example.interview_music.ui.music_player.components.MusicControls
import com.example.interview_music.ui.music_player.components.MusicDetails
import com.example.interview_music.ui.music_player.navigation.MusicPlayerRoute
import com.example.interview_music.ui.shared.ErrorSnackBar
import com.example.interview_music.ui.shared.InnerAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlayerScreen(
    onNavigateBack: () -> Unit = {},
    musicPlayerRoute: MusicPlayerRoute = MusicPlayerRoute(),
    viewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    val trackData by viewModel.trackData.collectAsStateWithLifecycle()
    val sliderPosition by viewModel.sliderPosition.collectAsStateWithLifecycle()
    val isOnRepeat by viewModel.isOnRepeat.collectAsStateWithLifecycle()
    val isOnShuffle by viewModel.isOnShuffle.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val errorOccurred by viewModel.errorOccurred.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        if (musicPlayerRoute.selectedTrackId != null) {
            viewModel.playSong(musicPlayerRoute)
        } else if (musicPlayerRoute.streamMusicAvailable) {
            viewModel.streamOnlineSongs()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                InnerAppBar(title = stringResource(R.string.playing_music), onNavigateBack = onNavigateBack)
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    ErrorSnackBar(data)
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(
                        top = 24.dp,
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                MusicDetails(trackData = trackData)
                Spacer(modifier = Modifier.height(24.dp))
                MusicControls(
                    sliderPosition = sliderPosition,
                    onSliderPositionChange = { value ->
                        viewModel.onSliderPositionChange(value)
                    },
                    onSliderPositionChangeFinished = {
                        viewModel.onSliderPositionChangeDone()
                    },
                    durationMs = trackData.duration ?: 0L,
                    onPlayPauseClick = {
                        viewModel.togglePlayPause()
                    },
                    onNextClick = {
                        viewModel.nextTrack()
                    },
                    onPreviousClick = {
                        viewModel.previousTrack()
                    },
                    onShuffleClick = {
                        viewModel.shufflePlaylist()
                    },
                    onRepeatClick = {
                        viewModel.repeatTrack()
                    },
                    onSeekForward = {
                        viewModel.seekForwardTo(1000L)
                    },
                    onSeekBack = {
                        viewModel.seekBackTo(1000L)
                    },
                    isPlaying = isPlaying,
                    isRepeat = isOnRepeat,
                    isShuffle = isOnShuffle,
                )
            }
        }
        val errorText = stringResource(id = R.string.error_loading)
        if (errorOccurred) {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(errorText)
                }
                viewModel.stopShowingError()
            }

        }
    }


}



