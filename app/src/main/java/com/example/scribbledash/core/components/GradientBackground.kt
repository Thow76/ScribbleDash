package com.example.scribbledash.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun GradientBackground(
    startColor: Color = Color(0xFFFEFAF6),
    endColor: Color = Color(0xFFFFF1E2),
    modifier: Modifier = Modifier.fillMaxSize(),
    content: @Composable BoxScope.() -> Unit
) {
    val brush = Brush.verticalGradient(colors = listOf(startColor, endColor))

    Box(
        modifier = modifier.background(brush)
    ) {
        content()
    }
}