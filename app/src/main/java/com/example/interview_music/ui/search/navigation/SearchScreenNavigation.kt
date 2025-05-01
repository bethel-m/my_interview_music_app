package com.example.interview_music.ui.search.navigation

import androidx.annotation.Keep
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.search.SearchScreen
import kotlinx.serialization.Serializable

@Keep
@Serializable
object SearchScreenRoute

fun NavGraphBuilder.searchScreenDestination(
    modifier: Modifier = Modifier,
    showErrorSnackBar: () -> Unit = {},
    musicPlayerViewModel: MusicPlayerViewModel,
    onNavigateToPlayer: () -> Unit = {},
) {
    composable<SearchScreenRoute> {
        SearchScreen(
            modifier = modifier,
            showErrorSnackBar = showErrorSnackBar,
            musicPlayerViewModel = musicPlayerViewModel,
            onNavigateToPlayer = onNavigateToPlayer,
        )
    }
}