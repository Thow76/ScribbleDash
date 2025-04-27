package com.example.scribbledash.features.drawscreen.presentation.state

import androidx.compose.ui.graphics.Color
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData

data class DrawingViewState(
    val paths: List<StrokeData> = emptyList(),
    val undonePaths: List<StrokeData> = emptyList(),
    val currentPath: StrokeData? = null,
    val selectedColor: Color = Color.Black,
    val thickness: Float = 5f
)



