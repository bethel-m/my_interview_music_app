package com.example.interview_music.ui.top_level_navigation

import com.example.interview_music.ui.home.navigation.HomeScreenRoute
import com.example.interview_music.ui.search.navigation.SearchScreenRoute
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.interview_music.R


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopLevelAppBar(currentDestination: String?) {
    TopAppBar(
        title = {
            Text(
                stringResource(
                    when (currentDestination) {
                        HomeScreenRoute::class.qualifiedName -> R.string.my_music_app
                        SearchScreenRoute::class.qualifiedName -> R.string.explore_music
                        else -> R.string.my_music_app
                    }
                )
            )
        },
    )
}