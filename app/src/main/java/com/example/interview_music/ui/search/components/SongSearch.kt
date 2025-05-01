package com.example.interview_music.ui.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.interview_music.R

@Composable
fun SongSearch(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    textValue: String = ""

) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textValue,
            shape = RoundedCornerShape(16.dp),
            onValueChange = onTextChange,
            placeholder = {Text(stringResource(R.string.search_placeholder))},
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            onSearch()
        }) { Text(stringResource(R.string.search)) }
    }
}