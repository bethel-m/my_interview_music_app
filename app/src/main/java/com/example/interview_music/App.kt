package com.example.interview_music

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.interview_music.ui.home.navigation.HomeScreenRoute
import com.example.interview_music.ui.home.navigation.homeScreenDestination
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.music_player.PlayerBottomNav
import com.example.interview_music.ui.music_player.navigation.MusicPlayerRoute
import com.example.interview_music.ui.music_player.navigation.musicPlayerScreenDestination
import com.example.interview_music.ui.search.navigation.searchScreenDestination
import com.example.interview_music.ui.shared.ErrorSnackBar
import com.example.interview_music.ui.songs_listing.navigation.SongsListingRoute
import com.example.interview_music.ui.songs_listing.navigation.songListingScreenDestination
import com.example.interview_music.ui.top_level_navigation.BottomNavigationBar
import com.example.interview_music.ui.top_level_navigation.TopLevelAppBar
import com.example.interview_music.ui.top_level_navigation.TopLevelDestinations
import kotlinx.coroutines.launch


@Composable
fun MyApp(
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val lastPlayedId = musicPlayerViewModel.lastPlayedId.collectAsStateWithLifecycle().value
    val hasLastPlayedId = lastPlayedId != null
    val showTopLevelNavigation =
        currentDestination in TopLevelDestinations.destinations.map { it.route::class.qualifiedName }
    val isMusicPlayerScreen =
        currentDestination?.startsWith(MusicPlayerRoute::class.qualifiedName.toString()) ?: false
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        musicPlayerViewModel.startPlaybackService(context)
    }
    LaunchedEffect(lastPlayedId) {
        if (lastPlayedId != null) {
            musicPlayerViewModel.resumeLastPlayedTrack(lastPlayedId)
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                AnimatedVisibility(visible = hasLastPlayedId && !isMusicPlayerScreen) {
                    PlayerBottomNav(
                        onNavigateToMusicPlayerScreen = {
                            navController.navigate(
                                MusicPlayerRoute(
                                    selectedTrackId = null,
                                    //  mediaList = emptyList(),
                                    streamMusicAvailable = false,
                                )
                            )
                        },
                        viewModel = musicPlayerViewModel,
                        modifier = if (!showTopLevelNavigation) {
                            Modifier.systemBarsPadding()
                        } else {
                            Modifier
                        }
                    )
                }

                AnimatedVisibility(visible = showTopLevelNavigation) {
                    BottomNavigationBar(navController)
                }
            }

        },
        topBar = {
            AnimatedVisibility(showTopLevelNavigation) {
                TopLevelAppBar(currentDestination)
            }

        },
        snackbarHost = {

            SnackbarHost(snackbarHostState) { data ->
                ErrorSnackBar(data)
            }
        },
    )
    { innerPadding ->
        val errorText = stringResource(id = R.string.error_loading)
        NavHost(navController = navController, startDestination = HomeScreenRoute) {
            homeScreenDestination(
                onAlbumNavigationToListingScreen = { albumId ->
                    navController.navigate(
                        SongsListingRoute(
                            albumId = albumId,
                            artistId = null
                        )
                    )
                },
                onStreamToMusicPlayerScreen = {
                    navController.navigate(
                        MusicPlayerRoute(
                            selectedTrackId = null,
                            streamMusicAvailable = true
                        )
                    )
                },
                onArtistNavigationToListingScreen = { artistId ->
                    navController.navigate(
                        SongsListingRoute(
                            albumId = null,
                            artistId = artistId
                        )
                    )
                },
                onNavigateToMusicPlayerScreen = { id ->
                    navController.navigate(
                        MusicPlayerRoute(
                            selectedTrackId = id,
                            // mediaList = mediaList,
                            streamMusicAvailable = false,
                        )
                    )
                },
                showErrorSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(errorText)
                    }
                },
                musicPlayerViewModel = musicPlayerViewModel,
                modifier = Modifier.padding(innerPadding)
            )
            searchScreenDestination(
                modifier = Modifier.padding(innerPadding),
                musicPlayerViewModel = musicPlayerViewModel,
                showErrorSnackBar = {
                    scope.launch {
                        snackbarHostState.showSnackbar(errorText)
                    }
                },
                onNavigateToPlayer = {
                    navController.navigate(
                        MusicPlayerRoute(
                            selectedTrackId = null,
                            // mediaList = emptyList(),
                            streamMusicAvailable = true
                        )
                    )
                },

                )

            songListingScreenDestination(
                onNavigateBack = { navController.navigateUp() },
                musicPlayerViewModel = musicPlayerViewModel,
                onNavigateToPlayer = {
                    navController.navigate(
                        MusicPlayerRoute(
                            selectedTrackId = null,
                            // mediaList = emptyList(),
                            streamMusicAvailable = true
                        )
                    )
                },
            )
            musicPlayerScreenDestination(
                onNavigateBack = { navController.navigateUp() },
                viewModel = musicPlayerViewModel,

                )
        }
    }
}

