package com.example.interview_music.ui.home.navigation

import androidx.annotation.Keep
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.interview_music.ui.home.HomeScreen
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
object HomeScreenRoute

fun NavGraphBuilder.homeScreenDestination(
    onNavigateToMusicPlayerScreen: (id: Int) -> Unit = {},
    showErrorSnackBar: () -> Unit = {},
    onAlbumNavigationToListingScreen: (id: Int) -> Unit = {},
    onArtistNavigationToListingScreen: (id: Int) -> Unit = {},
    onStreamToMusicPlayerScreen: () -> Unit = { },
    musicPlayerViewModel: MusicPlayerViewModel,
    modifier: Modifier =Modifier,
    ) {
    composable<HomeScreenRoute> {
        HomeScreen(
            onNavigateToMusicPlayerScreen = onNavigateToMusicPlayerScreen,
            onAlbumNavigationToListingScreen = onAlbumNavigationToListingScreen,
            onArtistNavigationToListingScreen = onArtistNavigationToListingScreen,
            onStreamToMusicPlayerScreen = onStreamToMusicPlayerScreen,
            modifier = modifier,
            showErrorSnackBar = showErrorSnackBar,
            musicPlayerViewModel = musicPlayerViewModel,
        )
    }
}