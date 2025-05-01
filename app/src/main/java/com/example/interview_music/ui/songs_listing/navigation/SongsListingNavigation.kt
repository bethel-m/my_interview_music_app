package com.example.interview_music.ui.songs_listing.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.songs_listing.SongListingScreen
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SongsListingRoute(
    val albumId: Int? = null,
    val artistId: Int? = null,
)

fun NavGraphBuilder.songListingScreenDestination(
    onNavigateBack: () -> Unit = {},
    onNavigateToPlayer: (Boolean) -> Unit = {},
    musicPlayerViewModel: MusicPlayerViewModel,
) {
    composable<SongsListingRoute> { backStackEntry ->
        val route: SongsListingRoute = backStackEntry.toRoute()
        SongListingScreen(
            songsListingRoute = route,
            onNavigateBack = onNavigateBack,
            onNavigateToPlayer = onNavigateToPlayer,
            musicPlayerViewModel = musicPlayerViewModel,
        )
    }
}