package com.example.interview_music.ui.music_player.components

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.interview_music.R

//the difference between this and GlideImage is that
// this rebuilds when recompositions occur, but GlideImage does not
@Composable
fun TrackImage(
    url: String,
    modifier: Modifier = Modifier,
) {

    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        },
        modifier = modifier
    )
}
