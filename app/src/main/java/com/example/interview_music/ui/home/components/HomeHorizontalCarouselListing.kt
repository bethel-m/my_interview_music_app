package com.example.interview_music.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.interview_music.data.tracks.models.Track
import com.example.interview_music.ui.shared.GlideImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeHorizontalCarouselListing(
    modifier: Modifier = Modifier,
    tracks: List<Track>,
    sectionTitle: String,
    onClick: (id: Int) -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        HomeSectionTitle(title = sectionTitle, hasAction = false)
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { tracks.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            CarouselCards(
                onClick = onClick, track = tracks[i]
            )
        }
    }
}

@Composable
private fun CarouselCards(
    onClick: (id: Int) -> Unit = {},
    track: Track,
) {
    Box(
        modifier = Modifier
            //  .size(width = 160.dp, height = 200.dp)
            //.background(Color.Gray)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(track.id) }
            .fillMaxSize()
    ) {
        GlideImage(
            url = track.image,
            modifier = Modifier.matchParentSize()
        )
//        Image(
//            painter = painterResource(id = R.drawable.davido),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.matchParentSize()
//        )
        // Overlay gradient at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.Black.copy(alpha = 0.6f)
                )
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            ) {
                Text(
                    text = track.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                )
                Text(
                    text = track.artistName,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                )
            }
        }
    }
}
