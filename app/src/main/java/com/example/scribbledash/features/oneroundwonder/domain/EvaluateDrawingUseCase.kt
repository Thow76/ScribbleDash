package com.example.scribbledash.features.oneroundwonder.domain

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import javax.inject.Inject
import kotlin.math.abs

class EvaluateDrawingUseCase @Inject constructor(
    private val drawingsRepository: DrawingsRepositoryInterface
) {
    suspend operator fun invoke(
        examplePath: String,
        userPaths: List<List<Offset>>,
        difficulty: Difficulty
    ): EvaluationResult {
        // Load example drawing
        val exampleBitmap = drawingsRepository.loadDrawingAsBitmap(examplePath)

        // Create user drawing bitmap
        val userBitmap = createBitmapFromPaths(userPaths, exampleBitmap.width, exampleBitmap.height)

        // Calculate similarity score (simplified example)
        val score = calculateSimilarityScore(exampleBitmap, userBitmap, difficulty)

        // Generate rating text based on score
        val rating = when {
            score >= 90 -> "Masterpiece!"
            score >= 70 -> "Great job!"
            score >= 50 -> "Not bad!"
            else -> "Keep practicing!"
        }

        return EvaluationResult(
            score = score,
            rating = rating,
            exampleBitmap = exampleBitmap.asImageBitmap(),
            userBitmap = userBitmap.asImageBitmap()
        )
    }

    private fun createBitmapFromPaths(paths: List<List<Offset>>, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }

        paths.forEach { points ->
            if (points.isNotEmpty()) {
                val path = Path()
                path.moveTo(points.first().x, points.first().y)
                for (i in 1 until points.size) {
                    path.lineTo(points[i].x, points[i].y)
                }
                canvas.drawPath(path, paint)
            }
        }

        return bitmap
    }

    private fun calculateSimilarityScore(
        example: Bitmap,
        user: Bitmap,
        difficulty: Difficulty
    ): Int {
        // Apply difficulty-based scoring
        val difficultyMultiplier = when (difficulty) {
            Difficulty.Beginner -> 1.2f
            Difficulty.Challenging -> 1.0f
            Difficulty.Master -> 0.8f
        }

        // Simplified pixel comparison (real implementation would be more sophisticated)
        var matchingPixels = 0
        var totalPixels = 0

        for (x in 0 until example.width) {
            for (y in 0 until example.height) {
                if (abs(example.getPixel(x, y) - user.getPixel(x, y)) < 50_000_000) {
                    matchingPixels++
                }
                totalPixels++
            }
        }

        val rawScore = (matchingPixels.toFloat() / totalPixels) * 100
        return (rawScore * difficultyMultiplier).toInt().coerceIn(0, 100)
    }
}