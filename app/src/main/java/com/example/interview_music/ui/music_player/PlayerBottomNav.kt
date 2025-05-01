package com.example.interview_music.ui.music_player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.interview_music.R
import com.example.interview_music.ui.music_player.components.TrackImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomNav(
    modifier: Modifier = Modifier,
    onNavigateToMusicPlayerScreen: () -> Unit = {},
    viewModel: MusicPlayerViewModel = hiltViewModel(),
) {
    val trackData by viewModel.trackData.collectAsStateWithLifecycle()
    val sliderPosition by viewModel.sliderPosition.collectAsStateWithLifecycle()
    var progress = (sliderPosition.toFloat()) / (trackData.duration?.toFloat() ?: 1f)
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    Card(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            ListItem(
                modifier = Modifier.clickable { onNavigateToMusicPlayerScreen()},
                trailingContent = {
                    Row {
                        IconButton(
                            onClick = {
                                viewModel.togglePlayPause()
                            },
                            modifier = Modifier
                                .size(20.dp) // now the IconButton itself is 150.dp
                                .background(color = Color.White, shape = CircleShape)

                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) {
                                        R.drawable.pause
                                    } else {
                                        R.drawable.play
                                    }
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                },
                headlineContent = {
                    Text(
                        trackData.songTitle,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                },
                supportingContent = { Text(trackData.artistName) },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        TrackImage(
                            url = trackData.songCoverArt,
                            modifier = Modifier.matchParentSize()
                        )
//                        Image(
//                            painter = painterResource(id = R.drawable.davido),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.matchParentSize()
//                        )
                    }
                }
            )
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth()
                //        color = COMPILED_CODE,
                //        trackColor = COMPILED_CODE,
                //        strokeCap = COMPILED_CODE,
            )

            //  Slider()
        }
    }
}