package com.example.interview_music.ui.search.components


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.ui.shared.MusicTile

@Composable
fun SearchResultListings(
    tracks: List<Track>,
    modifier: Modifier = Modifier,
    onPressed: (id: Int) -> Unit = {}
) {
    LazyColumn {
        items(tracks.size) { index ->
            MusicTile(
                tracks[index],
                onPressed = {
                    onPressed(index)
                }
            )
        }

    }
}