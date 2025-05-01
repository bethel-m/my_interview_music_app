package com.example.interview_music.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.interview_music.data.tracks.models.Track

@Composable
fun MusicTile(
    track: Track,
    modifier: Modifier = Modifier,
    onPressed: (id: Int) -> Unit = {}
) {
    ListItem(
        modifier = modifier.clickable { onPressed(track.id) },
        headlineContent = {
            Text(
                track.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        supportingContent = { Text(track.artistName) },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                GlideImage(
                    url = track.image,
                    modifier = Modifier.matchParentSize()
                )
//                Image(
//                    painter = painterResource(id = R.drawable.davido),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize()
//                )
            }
        }
    )
}


