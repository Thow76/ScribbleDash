package com.example.scribbledash.features.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

/** Utility to compute bounding rectangles for a collection of points. */
object BoundsCalculator {
    /**
     * Calculates the bounding [Rect] surrounding [points].
     *
     * @param points list of points to bound
     * @param paddingPercent optional extra padding applied on all sides
     * @return bounding [Rect] with the requested padding
     */
    fun calculateBounds(points: List<Offset>, paddingPercent: Float = 0f): Rect {
        if (points.isEmpty()) {
            val base = 1f
            val padX = base * paddingPercent
            val padY = base * paddingPercent
            return Rect(-padX, -padY, base + padX, base + padY)
        }

        val minX = points.minOfOrNull { it.x } ?: 0f
        val maxX = points.maxOfOrNull { it.x } ?: (minX + 1f)
        val minY = points.minOfOrNull { it.y } ?: 0f
        val maxY = points.maxOfOrNull { it.y } ?: (minY + 1f)
        val width = (maxX - minX).takeIf { it > 0f } ?: 1f
        val height = (maxY - minY).takeIf { it > 0f } ?: 1f

        val paddingX = width * paddingPercent
        val paddingY = height * paddingPercent

        return Rect(
            minX - paddingX,
            minY - paddingY,
            minX + width + paddingX,
            minY + height + paddingY
        )
    }
}
