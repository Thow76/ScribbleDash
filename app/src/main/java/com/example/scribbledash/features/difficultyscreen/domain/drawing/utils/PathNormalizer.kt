package com.example.scribbledash.features.utils

import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.util.Log

private const val TAG = "PathNormalizer"

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
        if (paths.isEmpty()) {
            Log.d(TAG, "normalizePaths: Empty paths list, returning empty list")
            return emptyList()
        }

        Log.d(TAG, "normalizePaths: Input params - paths.size=${paths.size}, " +
                "strokeWidth=$strokeWidth, exampleStrokeWidth=$exampleStrokeWidth, " +
                "canvasSizePx=$canvasSizePx")

        // 1) Compute union of all path bounds
        val totalBounds = RectF().apply {
            paths.forEach { path ->
                val bounds = RectF().also { path.computeBounds(it, /* exact = */ true) }
                union(bounds)
                Log.v(TAG, "Path bounds: $bounds")
            }
        }
        Log.d(TAG, "Total bounds: $totalBounds (width=${totalBounds.width()}, height=${totalBounds.height()})")

        // 2) Inset by half the stroke widths (user + extra from example)
        val insetBy    = strokeWidth / 2f
        val extraInset = (exampleStrokeWidth - strokeWidth) / 2f
        val insetBounds = RectF(totalBounds).apply {
            inset(insetBy + extraInset, insetBy + extraInset)
        }
        Log.d(TAG, "Inset calculation: insetBy=$insetBy, extraInset=$extraInset")
        Log.d(TAG, "Inset bounds: $insetBounds (width=${insetBounds.width()}, height=${insetBounds.height()})")

        // 3) Compute translation to bring top-left to (0,0).
        //    This keeps the normalized paths anchored at the origin,
        //    unlike [scaleImage] which recenters drawings for display.
        val translateX = -insetBounds.left
        val translateY = -insetBounds.top
        Log.d(TAG, "Translation: dx=$translateX, dy=$translateY")

        // 4) Compute uniform scale to fit the inset bounds into the canvas
        val scaleX = canvasSizePx / insetBounds.width()
        val scaleY = canvasSizePx / insetBounds.height()
        val scale  = minOf(scaleX, scaleY)
        Log.d(TAG, "Scale calculations: scaleX=$scaleX, scaleY=$scaleY, final scale=$scale")

        // 5) Build transform matrix (translate then scale)
        val transform = Matrix().apply {
            postTranslate(translateX, translateY)
            postScale(scale, scale)
        }
        // Log matrix values to verify transform
        val values = FloatArray(9)
        transform.getValues(values)
        Log.d(TAG, "Transform matrix: scaleX=${values[Matrix.MSCALE_X]}, " +
                "scaleY=${values[Matrix.MSCALE_Y]}, " +
                "translateX=${values[Matrix.MTRANS_X]}, " +
                "translateY=${values[Matrix.MTRANS_Y]}")

        // 6) Apply transform to each path and return new list
        return paths.map { original ->
            Path(original).apply {
                transform(transform)
                // Log bounds after transformation to verify
                val transformedBounds = RectF()
                computeBounds(transformedBounds, true)
                Log.v(TAG, "Transformed path bounds: $transformedBounds")
            }
        }.also {
            Log.d(TAG, "Normalized ${it.size} paths")
        }
    }
}
