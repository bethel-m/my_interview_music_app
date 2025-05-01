package com.example.interview_music.ui.music_player.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.interview_music.ui.music_player.TrackData
import com.example.interview_music.ui.shared.GlideImage

@Composable
fun MusicDetails(
    trackData: TrackData
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            if (trackData.songTitle.isEmpty() || trackData.songTitle == "null") {
                GlideImage(
                    url = trackData.songCoverArt,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                TrackImage(
                    url = trackData.songCoverArt,
                    modifier = Modifier.fillMaxSize()
                )
            }

//            Image(
//                painter = painterResource(id = R.drawable.cover),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )

        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (trackData.songTitle.isEmpty() || trackData.songTitle == "null") "Song Title" else {
                trackData.songTitle
            },
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            ),
        )
        Text(
            text = if (trackData.artistName.isEmpty() || trackData.artistName == "null") "Song Title" else {
                trackData.artistName
            },
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
        )
    }
}
