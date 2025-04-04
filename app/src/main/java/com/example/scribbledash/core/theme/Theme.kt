package com.example.scribbledash.features.theme

import androidx.compose.material3.lightColorScheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush

private val LightColorScheme = lightColorScheme(
    primary = ScribbleColors.Primary,
    onPrimary = ScribbleColors.OnPrimary,
    secondary = ScribbleColors.Secondary,
    error = ScribbleColors.Error,
    background = ScribbleColors.Background,
    surface = ScribbleColors.SurfaceHigh,
    onBackground = ScribbleColors.OnBackground,
    onSurface = ScribbleColors.OnSurface,
)

@Composable
fun ScribbleDashTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        content = content
    )
}

