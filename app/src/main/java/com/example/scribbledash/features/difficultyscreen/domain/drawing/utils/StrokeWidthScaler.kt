package com.example.scribbledash.features.utils

import com.example.scribbledash.features.difficultyscreen.domain.Difficulty

/**
 * Milestone 2 Step 1:
 * Scale the example drawing’s stroke width by a difficulty‐dependent multiple
 * of the user’s stroke width.
 */
object StrokeWidthScaler {

    /**
     * @param userStrokeWidth the width the user drew with (in pixels)
     * @param difficulty      game difficulty
     * @return                the stroke width to use when drawing the example,
     *                       before normalization and bitmap rendering
     */
    fun computeExampleStrokeWidth(
        userStrokeWidth: Float,
        difficulty: Difficulty
    ): Float {
        val multiplier = when (difficulty) {
            Difficulty.Beginner    -> 15f
            Difficulty.Challenging ->  7f
            Difficulty.Master      ->  4f
        }
        return userStrokeWidth * multiplier
    }
}