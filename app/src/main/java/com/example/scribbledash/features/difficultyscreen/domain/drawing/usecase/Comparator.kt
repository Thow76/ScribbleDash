package com.example.scribbledash.features.difficultyscreen.domain.drawing.usecase

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.asAndroidPath
import androidx.core.graphics.PathParser
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.utils.BitmapRenderer
import com.example.scribbledash.features.utils.CoverageCalculator
import com.example.scribbledash.features.utils.PathNormalizer
import com.example.scribbledash.features.utils.PixelComparator
import com.example.scribbledash.features.utils.StrokeWidthScaler
import com.example.scribbledash.features.utils.DrawingUtils

object Comparator : DrawingComparer {
    private const val CANVAS_SIZE_PX = 512
    /**
     * Full Milestone 2 pipeline:
     * 1) scale example stroke width
     * 2) convert offsets & SVG pathData to Android Paths
     * 3) normalize (offset + scale) both sets
     * 4) render to Bitmaps
     * 5) pixel-by-pixel compare → ratio
     * 6) convert to 0–100%
     *
     * @param userPaths         List of user strokes (points)
     * @param examplePathData   List of SVG “d” strings for the target
     * @param userStrokeWidth   Stroke width user drew with
     * @param difficulty        Game difficulty
     * @param canvasSizePx      Canvas size for comparison (square)
     * @return                  Accuracy percentage [0..100]
     */
    override fun compare(
        userPaths: List<List<Offset>>,
        exampleData: List<String>,
        userStrokeWidth: Float,
        difficulty: Difficulty
    ): Int {
        // Step 1: scale example stroke width by difficulty
        val exampleStrokeW = StrokeWidthScaler.computeExampleStrokeWidth(
            userStrokeWidth, difficulty
        )

        // Step 2a: build Compose AndroidPaths (for Bézier smoothing)
        val userAndroidPaths: List<AndroidPath> =
            userPaths.map { offsetsToAndroidPathSmooth(it) }

        // Step 2b: unwrap to framework Paths
        val userRawPaths: List<Path> =
            userAndroidPaths.map { it.asAndroidPath() }

        // Step 2c: parse example SVG into framework Paths
        val exampleRawPaths: List<Path> =
            exampleData.map { PathParser.createPathFromPathData(it) }

        // Step 3: normalize both sets of framework Paths
        val normUser: List<Path> = PathNormalizer.normalizePaths(
            paths              = userRawPaths,
            strokeWidth        = userStrokeWidth,
            exampleStrokeWidth = exampleStrokeW,
            canvasSizePx       = CANVAS_SIZE_PX
        )
        val normExample: List<Path> = PathNormalizer.normalizePaths(
            paths              = exampleRawPaths,
            strokeWidth        = exampleStrokeW,
            exampleStrokeWidth = exampleStrokeW,
            canvasSizePx       = CANVAS_SIZE_PX
        )

        // Step 4: render each to a bitmap
        val userBmp    = BitmapRenderer.drawPathsToBitmap(normUser,    userStrokeWidth, CANVAS_SIZE_PX)
        val exampleBmp = BitmapRenderer.drawPathsToBitmap(normExample, exampleStrokeW,   CANVAS_SIZE_PX)

        // Step 5: pixel‐by‐pixel coverage ratio
        val coverageRatio = PixelComparator.pixelCoverage(userBmp, exampleBmp)

        // Step 6: convert ratio to 0–100%
        return CoverageCalculator.computeCoveragePercent(coverageRatio)
    }

    private fun offsetsToAndroidPath(points: List<Offset>): AndroidPath {
        val path = AndroidPath()
        if (points.isEmpty()) return path
        path.moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }
        return path
    }

    /**
     * Build a *smoothed* AndroidPath from raw touch offsets,
     * mirroring your Compose createSmoothPath() logic.
     */
    private fun offsetsToAndroidPathSmooth(points: List<Offset>): AndroidPath {
        return DrawingUtils.smoothPath(points).asAndroidPath()
    }
}


