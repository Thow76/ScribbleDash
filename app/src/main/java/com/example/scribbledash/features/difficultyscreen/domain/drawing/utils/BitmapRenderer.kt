package com.example.scribbledash.features.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

/**
 * Milestone 2 Step 3:
 * Render a list of normalized Android Paths onto a transparent Bitmap.
 */
object BitmapRenderer {

    /**
     * @param paths        List of Android Paths, already translated & scaled to fit in the square.
     * @param strokeWidth  Stroke width (in px) to draw each path.
     * @param canvasSizePx Width and height (in px) of the square Bitmap.
     * @return             A transparent Bitmap of size canvasSizePx×canvasSizePx
     *                     containing only the stroked paths.
     */
    fun drawPathsToBitmap(
        paths: List<Path>,
        strokeWidth: Float,
        canvasSizePx: Int
    ): Bitmap {
        // 1) Create an ARGB_8888 Bitmap with full transparency
        val bitmap = Bitmap.createBitmap(canvasSizePx, canvasSizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 2) Configure paint for stroking
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = android.graphics.Color.BLACK      // opaque black; any non-transparent color works
            this.strokeWidth = strokeWidth
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        // 3) Draw each normalized path onto the canvas
        paths.forEach { path ->
            canvas.drawPath(path, paint)
        }

        return bitmap
    }
}
