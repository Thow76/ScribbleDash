package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPathData: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val pathIndices: List<Int> = emptyList(),
    val redoIndices: List<Int> = emptyList(),
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
    val hasPaths: Boolean = false,
    val visiblePathCount: Int = 0


)

data class PathData(
    val path: Path = Path(),
    val color: Color,
    val points: List<Offset> = emptyList()

)


