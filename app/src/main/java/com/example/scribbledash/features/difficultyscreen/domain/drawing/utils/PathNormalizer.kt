package com.example.scribbledash.features.utils

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.ui.graphics.AndroidPath

object PathNormalizer {
    /**
     * Milestone 2 Step 2: normalize a list of Android Paths
     * by insetting, translating, and uniformly scaling them
     * so that they all fit into a square canvas.
     *
     * @param paths               the original Android Paths
     * @param strokeWidth         the stroke width used to draw these paths
     * @param exampleStrokeWidth  the scaled example stroke width
     *                            (for user paths this may differ from strokeWidth)
     * @param canvasSizePx        size in pixels of the square comparison canvas
     *
     * @return a new List<Path> where each Path has been transformed
     */
    fun normalizePaths(
        paths: List<Path>,
        strokeWidth: Float,
        exampleStrokeWidth: Float,
        canvasSizePx: Int
    ): List<Path> {
        if (paths.isEmpty()) return emptyList()

        // 1) Compute union of all path bounds
        val totalBounds = RectF().apply {
            paths.forEach { path ->
                val bounds = RectF().also { path.computeBounds(it, /* exact = */ true) }
                union(bounds)
            }
        }

        // 2) Inset by half the stroke widths (user + extra from example)
        val insetBy    = strokeWidth / 2f
        val extraInset = (exampleStrokeWidth - strokeWidth) / 2f
        val insetBounds = RectF(totalBounds).apply {
            inset(insetBy + extraInset, insetBy + extraInset)
        }

        // 3) Compute translation to bring top-left to (0,0)
        val translateX = -insetBounds.left
        val translateY = -insetBounds.top

        // 4) Compute uniform scale to fit the inset bounds into the canvas
        val scaleX = canvasSizePx / insetBounds.width()
        val scaleY = canvasSizePx / insetBounds.height()
        val scale  = minOf(scaleX, scaleY)

        // 5) Build transform matrix (translate then scale)
        val transform = Matrix().apply {
            postTranslate(translateX, translateY)
            postScale(scale, scale)
        }

        // 6) Apply transform to each path and return new list
        return paths.map { original ->
            Path(original).apply { transform(transform) }
        }
    }
}
