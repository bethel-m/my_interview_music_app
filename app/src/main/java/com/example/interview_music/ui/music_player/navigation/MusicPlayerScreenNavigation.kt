package com.example.interview_music.ui.music_player.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.interview_music.ui.music_player.MusicPlayerScreen
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MusicPlayerRoute(
    val selectedTrackId: Int? = null,
   // val mediaList: List<Int> = emptyList(),
    val streamMusicAvailable: Boolean = false,
)


fun NavGraphBuilder.musicPlayerScreenDestination(
    onNavigateBack: () -> Unit = {},
    viewModel: MusicPlayerViewModel,
) {
    composable<MusicPlayerRoute> { backStackEntry ->
        val route: MusicPlayerRoute = backStackEntry.toRoute()
        MusicPlayerScreen(
            onNavigateBack = onNavigateBack,
            viewModel = viewModel,
            musicPlayerRoute = route,
        )
    }
}