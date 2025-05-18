package com.example.scribbledash.features.drawscreen.components

import androidx.compose.ui.geometry.Rect
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData

/**
 * Computes scale and translation to fit `paths` and `currentPath` into a canvas of size
 * (canvasW, canvasH). If `boundingBox` is non-null, it will be used directly; otherwise
 * the bounding box is computed from the provided paths. When `autoFit` is false, returns
 * identity transform (scale=1, dx=0, dy=0).
 */
fun scaleImage(
    paths: List<StrokeData>,
    currentPath: StrokeData?,
    boundingBox: Rect?,
    autoFit: Boolean,
    canvasW: Float,
    canvasH: Float

): Triple<Float, Float, Float> {
    if (!autoFit) return Triple(1f, 0f, 0f)

    // Determine bounding box
    val box = boundingBox ?: run {
        val allPoints = paths.flatMap { it.points } + (currentPath?.points ?: emptyList())
        val minX = allPoints.minOfOrNull { it.x } ?: 0f
        val maxX = allPoints.maxOfOrNull { it.x } ?: (minX + 1f)
        val minY = allPoints.minOfOrNull { it.y } ?: 0f
        val maxY = allPoints.maxOfOrNull { it.y } ?: (minY + 1f)
        val width = (maxX - minX).takeIf { it > 0f } ?: 1f
        val height = (maxY - minY).takeIf { it > 0f } ?: 1f
        Rect(minX, minY, minX + width, minY + height)
    }

    // Compute scale
    val drawingWidth = box.width.takeIf { it > 0f } ?: 1f
    val drawingHeight = box.height.takeIf { it > 0f } ?: 1f
    val scale = minOf(canvasW / drawingWidth, canvasH / drawingHeight)
    // Center offsets
    val dx = (canvasW - drawingWidth * scale) / 2f - box.left * scale
    val dy = (canvasH - drawingHeight * scale) / 2f - box.top * scale
    return Triple(scale, dx, dy)
}