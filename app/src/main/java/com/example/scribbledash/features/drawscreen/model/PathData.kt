package com.example.scribbledash.features.drawscreen.presentation.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

data class StrokeData(
    val points: List<Offset> = emptyList<Offset>(),
    val color: Color,
    val strokeWidth: Float,
    // Optional: val id: String = UUID.randomUUID().toString()
) {
    fun toStroke() = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
}