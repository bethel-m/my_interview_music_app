package com.example.interview_music.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.ui.shared.MusicTile

@Composable
fun HomeMusicTileListing(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    tracks: List<Track>,
    onMusicTilePressed: (id: Int) -> Unit = {},
    onMorePressed: () -> Unit = {}
) {
    Column(modifier = modifier) {
        HomeSectionTitle(title = sectionTitle, hasAction = false)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3), // 3 rows
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(tracks.size) { index ->
                MusicTile(
                    track = tracks[index],
                    onPressed = onMusicTilePressed
                )
            }

        }
    }
}

