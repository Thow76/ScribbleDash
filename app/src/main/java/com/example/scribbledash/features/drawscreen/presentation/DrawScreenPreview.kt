package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.scribbledash.features.drawscreen.presentation.components.createSmoothPath

@Composable
fun DrawScreenPreview(targetPaths: List<List<Offset>>, countdown: Int) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxWidth().aspectRatio(1f)) {
            targetPaths.forEach { pts ->
                drawPath(createSmoothPath(pts), color = Color.LightGray, style = Stroke(4f))
            }
        }
        Text(
            text = countdown.toString(),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}