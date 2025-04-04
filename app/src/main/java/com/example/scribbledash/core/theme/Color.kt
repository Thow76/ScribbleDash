package com.example.scribbledash.features.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object ScribbleColors {

    val Primary = Color(0xFF238CFF)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnPrimary40 = Color(0x66FFFFFF)

    val Secondary = Color(0xFFAB5CFA)
    val TertiaryContainer = Color(0xFFFA852C)

    val Error = Color(0xFFEF1242)
    val Success = Color(0xFF0DD280)

    val Background = Color(0xFFFEFAF6)
    val BackgroundGradientStart = Color(0xFFFEFAF6)
    val BackgroundGradientEnd = Color(0xFFFFF1E2)

    val OnBackground = Color(0xFF514437)
    val OnBackgroundVar = Color(0xFF7F7163)

    val SurfaceHigh = Color(0xFFFFFFFF)
    val SurfaceOpacity80 = Color(0xCCFFFFFF)
    val SurfaceLow = Color(0xFFEEE7E0)
    val SurfaceLowest = Color(0xFFE1D5CA)

    val OnSurface = Color(0xFFA5978A)
    val OnSurfaceVar = Color(0xFFF6F1EC)
}

object Gradient {

        val gradientStart = ScribbleColors.BackgroundGradientStart
        val gradientEnd = ScribbleColors.BackgroundGradientEnd

        val mainGradient = Brush.linearGradient(
            colors = listOf(gradientStart, gradientEnd),
            start = androidx.compose.ui.geometry.Offset.Zero,
            end = androidx.compose.ui.geometry.Offset.Infinite)
}
