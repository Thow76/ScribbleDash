package com.example.scribbledash.features.difficultyscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.scribbledash.R
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty

@Composable
fun DifficultyOptions(
    difficulty: Difficulty,
    index: Int,
    onClick: () -> Unit
) {
    // Get vertical offset based on index
    val yOffset = when (index) {
        0 -> 4.dp
        1 -> (-15).dp
        else -> 4.dp
    }

    // Get resource ID based on difficulty
    val resourceId = when (difficulty.name.lowercase()) {
        "beginner" -> R.drawable.beginner
        "challenging" -> R.drawable.challenging
        "master" -> R.drawable.master
        else -> R.drawable.beginner
    }

    // Get alignment based on difficulty
    val alignment = when (difficulty.name.lowercase()) {
        "beginner" -> Alignment.TopEnd
        "challenging" -> Alignment.BottomCenter
        "master" -> Alignment.Center
        else -> Alignment.Center
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            //.weight(1f)
            .offset(y = yOffset)
            .clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier.size(90.dp),
            shape = CircleShape,
            shadowElevation = 3.dp,
            color = Color.White
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = alignment
            ) {
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = difficulty.title,
                    contentScale = ContentScale.None,
                )
            }
        }

        Text(
            modifier = Modifier.offset(y = 12.dp),
            text = difficulty.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF514437)
        )
    }
}