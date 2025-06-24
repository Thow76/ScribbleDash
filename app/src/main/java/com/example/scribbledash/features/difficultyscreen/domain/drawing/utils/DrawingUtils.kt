package com.example.scribbledash.features.utils

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

private const val TAG = "DrawingUtils"

/**
 * Utility functions related to drawing paths.
 */
object DrawingUtils {
    /**
     * Create a smooth [Path] through the given [points] using quadratic Bézier curves.
     */
    fun smoothPath(points: List<Offset>): Path {
        val path = Path()
        if (points.isEmpty()) {
            Log.d(TAG, "smoothPath: Empty points list")
            return path
        }
        if (points.size == 1) {
            Log.d(TAG, "smoothPath: Single point, creating minimal line")
            path.moveTo(points[0].x, points[0].y)
            path.lineTo(points[0].x, points[0].y + 0.1f)
            return path
        }

        path.moveTo(points[0].x, points[0].y)
        if (points.size == 2) {
            Log.d(TAG, "smoothPath: Two points, creating direct line")
            path.lineTo(points[1].x, points[1].y)
        } else {
            Log.v(TAG, "smoothPath: ${points.size} points, creating smooth path with bezier curves")
            for (i in 1 until points.size) {
                if (i < points.size - 1) {
                    val xc = (points[i].x + points[i + 1].x) / 2f
                    val yc = (points[i].y + points[i + 1].y) / 2f
                    path.quadraticBezierTo(points[i].x, points[i].y, xc, yc)
                } else {
                    path.lineTo(points[i].x, points[i].y)
                }
            }
        }
        return path
    }
}
