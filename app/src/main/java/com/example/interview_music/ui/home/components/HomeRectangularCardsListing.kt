package com.example.interview_music.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.interview_music.data.albums.models.Album
import com.example.interview_music.ui.shared.GlideImage

@Composable
fun HomeRectangularCardsListing(
    albums: List<Album>,
    sectionTitle: String,
    modifier: Modifier = Modifier,
    onAlbumPressed: (id: Int) -> Unit = {},
    ) {
    Column(modifier = modifier) {
        HomeSectionTitle(title = sectionTitle, hasAction = false)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(albums.size) { index ->
                RectangularCards(albums[index], onAlbumPressed = onAlbumPressed)
            }
        }
    }
}

@Composable
private fun RectangularCards(
    album: Album,
    onAlbumPressed: (id: Int) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .clickable {
                onAlbumPressed(album.id)
            }
            .width(150.dp)

    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(150.dp)

                    .clip(RoundedCornerShape(16.dp))
            ) {
                GlideImage(
                    url = album.image,
                    modifier = Modifier.matchParentSize(),
                )
//                Image(
//                    painter = painterResource(id = R.drawable.davido),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize()
//                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = album.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = album.artistName,
            )
        }
    }
}
