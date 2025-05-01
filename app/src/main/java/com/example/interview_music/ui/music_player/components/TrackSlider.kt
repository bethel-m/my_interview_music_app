package com.example.interview_music.ui.music_player.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun TrackSlider(
    value: Float,
    onValueChange: (newValue: Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    songDuration: Float,
    modifier: Modifier = Modifier
) {
    Slider(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        onValueChangeFinished = {

            onValueChangeFinished()

        },
        valueRange = 0f..songDuration,
//        colors = SliderDefaults.colors(
//            thumbColor = Color.Black,
//            activeTrackColor = Color.DarkGray,
//            inactiveTrackColor = Color.Gray,
//        )
    )
}