package com.example.interview_music.ui.music_player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.interview_music.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicControls(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    onPlayPauseClick: () -> Unit = {},
    onShuffleClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    sliderPosition: Float = 0f,
    onSliderPositionChange: (Float) -> Unit = {},
    onSliderPositionChangeFinished: () -> Unit = {},
    durationMs: Long = 0L,
    onSeekBack: (Long) -> Unit = {},
    onSeekForward: (Long) -> Unit = {},
    isPlaying: Boolean = false,
    isRepeat: Boolean = false,
    isShuffle: Boolean = false,
) {

    //  var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    onSeekBack(1000L)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.rotate_left),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(onClick = { onSeekForward(1000L) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.rotate_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Slider(

                modifier = Modifier
                    .fillMaxWidth(),
                valueRange = 0f..durationMs.toFloat(),
                value = sliderPosition,
                onValueChange = onSliderPositionChange,
                onValueChangeFinished = onSliderPositionChangeFinished,
                thumb = {
                    SliderDefaults.Thumb(
                        interactionSource = remember { MutableInteractionSource() },
                        thumbSize = DpSize(16.dp, 16.dp),
                    )
                },
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(sliderPosition.toLong()))
                Text(formatTime(durationMs))
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onRepeatClick,
                modifier = if (isRepeat) {
                    Modifier
                        .size(20.dp)
                        .background(color = Color.White, shape = CircleShape)
                } else {
                    Modifier
                }

            ) {

                Icon(
                    painter = painterResource(id = R.drawable.repeate),
                    contentDescription = null,
                    tint = if (isRepeat) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
            IconButton(onClick = onPreviousClick) {
                Icon(
                    painter = painterResource(id = R.drawable.previous),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = onPlayPauseClick,
                modifier = Modifier
                    .size(80.dp)
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
            IconButton(onClick = onNextClick) {
                Icon(
                    painter = painterResource(id = R.drawable.next),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                onClick = onShuffleClick,
                modifier = if (isShuffle) {
                    Modifier
                        .size(20.dp)
                        .background(color = Color.White, shape = CircleShape)
                } else {
                    Modifier
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shuffle),
                    contentDescription = null,
                    tint = if (isShuffle) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }

        }
    }
}

fun formatTime(milliseconds: Long): String {
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
