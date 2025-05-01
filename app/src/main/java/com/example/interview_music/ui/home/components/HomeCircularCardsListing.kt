package com.example.interview_music.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.interview_music.data.artists.models.Artist
import com.example.interview_music.ui.shared.GlideImage

@Composable
fun HomeCircularCardsListing(
    sectionTitle: String,
    artists: List<Artist>,
    modifier: Modifier = Modifier,
    onArtistPressed: (id: Int) -> Unit = {},
) {
    Column(modifier = modifier) {
        HomeSectionTitle(title = sectionTitle, hasAction = false)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(artists.size) { index ->
                CircularCards(
                    artist = artists[index],
                    onArtistPressed = onArtistPressed
                )
            }
        }
    }
}

@Composable
private fun CircularCards(
    artist: Artist,
    modifier: Modifier = Modifier,
    onArtistPressed: (id: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.clickable { onArtistPressed(artist.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
        ) {
            GlideImage(
                url = artist.image,
                modifier = Modifier.matchParentSize()
            )
//            Image(
//                painter = painterResource(id = R.drawable.davido),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.matchParentSize()
//            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = artist.name,
            style = MaterialTheme.typography.titleMedium,
        )

    }
}
