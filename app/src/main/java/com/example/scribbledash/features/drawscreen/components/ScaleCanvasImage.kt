package com.example.scribbledash.features.drawscreen.components

import android.util.Log
import androidx.compose.ui.geometry.Rect
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData

private const val TAG = "ScaleCanvasImage"

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
    Log.d(TAG, "scaleImage: Input params - paths.size=${paths.size}, " +
            "currentPath=${if (currentPath != null) "present" else "null"}, " +
            "boundingBox=${boundingBox?.toString() ?: "null"}, " +
            "autoFit=$autoFit, canvasW=$canvasW, canvasH=$canvasH")

    if (!autoFit) {
        Log.d(TAG, "scaleImage: autoFit is false, returning identity transform (1, 0, 0)")
        return Triple(1f, 0f, 0f)
    }

    // Determine bounding box
    val box = boundingBox ?: run {
        Log.d(TAG, "scaleImage: Computing bounding box from paths")
        val allPoints = paths.flatMap { it.points } + (currentPath?.points ?: emptyList())

        if (allPoints.isEmpty()) {
            Log.w(TAG, "scaleImage: No points found to create bounding box")
        }

        val minX = allPoints.minOfOrNull { it.x } ?: 0f
        val maxX = allPoints.maxOfOrNull { it.x } ?: (minX + 1f)
        val minY = allPoints.minOfOrNull { it.y } ?: 0f
        val maxY = allPoints.maxOfOrNull { it.y } ?: (minY + 1f)
        val width = (maxX - minX).takeIf { it > 0f } ?: 1f
        val height = (maxY - minY).takeIf { it > 0f } ?: 1f

        Log.d(TAG, "Calculated bounds: minX=$minX, maxX=$maxX, minY=$minY, maxY=$maxY, width=$width, height=$height")
        Rect(minX, minY, minX + width, minY + height)
    }

    Log.d(TAG, "Using bounding box: $box (width=${box.width}, height=${box.height})")

    // Compute scale
    val drawingWidth = box.width.takeIf { it > 0f } ?: 1f
    val drawingHeight = box.height.takeIf { it > 0f } ?: 1f
    val scaleX = canvasW / drawingWidth
    val scaleY = canvasH / drawingHeight
    val scale = minOf(scaleX, scaleY)

    Log.d(TAG, "Scale calculations: scaleX=$scaleX, scaleY=$scaleY, final scale=$scale")

    // Center offsets
    val dx = (canvasW - drawingWidth * scale) / 2f - box.left * scale
    val dy = (canvasH - drawingHeight * scale) / 2f - box.top * scale

    Log.d(TAG, "Offset calculations: dx=$dx, dy=$dy")

    return Triple(scale, dx, dy).also {
        Log.d(TAG, "Final transformation: scale=${it.first}, dx=${it.second}, dy=${it.third}")
    }
}