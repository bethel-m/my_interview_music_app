package com.example.interview_music.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.ui.music_player.MusicPlayerViewModel
import com.example.interview_music.ui.search.components.SearchResultListings
import com.example.interview_music.ui.search.components.SongSearch
import com.example.interview_music.ui.search.components.SongTagListings
import com.example.interview_music.ui.shared.UiLoading

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    showErrorSnackBar: () -> Unit = {},
    onNavigateToPlayer: () -> Unit = {},
) {
    val searchQuery = viewModel.searchQuery
    val tracks = viewModel.searchResults.collectAsStateWithLifecycle()
    val isLoadingMore = viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val showError = viewModel.showError.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
            )
            .fillMaxSize()
    ) {
        if (isLoadingMore.value) {
            UiLoading()
        }
        SongSearch(
            textValue = searchQuery,
            onTextChange = { viewModel.updateSearchQuery(it) },
            onSearch = {
                if (searchQuery.isNotEmpty() && !isLoadingMore.value) {
                    viewModel.search(searchQuery)
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (tracks.value.isEmpty()) {
            SongTagListings(
                onClickTag = { tag ->
                    if (!isLoadingMore.value) {
                        viewModel.search(tag)
                    }
                }
            )
        } else {
            SearchResultListings(
                tracks = tracks.value,
                onPressed = {
                    val streamAllTracks = mutableListOf<Track>()
                    streamAllTracks.addAll(tracks.value)
                    streamAllTracks[0] = tracks.value[it]
                    musicPlayerViewModel.addStreamTracks(streamAllTracks)
                    onNavigateToPlayer()
                }
            )
        }

    }
if(showError.value){
    showErrorSnackBar()
    viewModel.stopShowingError()
}
}
