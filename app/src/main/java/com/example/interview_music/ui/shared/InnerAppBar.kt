package com.example.interview_music.ui.shared

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.interview_music.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InnerAppBar(title: String, onNavigateBack: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = ""
                )
            }

        },
        title = {
            Text(title)
        }
    )
}