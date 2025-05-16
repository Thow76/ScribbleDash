package com.example.scribbledash.features.difficultyscreen.domain.drawing.usecase

import androidx.compose.ui.geometry.Offset
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import javax.inject.Inject

interface DrawingComparer {
    /**
     * @param userPaths        List of user strokes (each a List<Offset>)
     * @param exampleData      List of SVG “d” strings for the reference drawing
     * @param userStrokeWidth  the width the user chose
     * @param difficulty       game difficulty
     * @return                 Accuracy percentage [0..100]
     */
    fun compare(
        userPaths: List<List<Offset>>,
        exampleData: List<String>,
        userStrokeWidth: Float,
        difficulty: Difficulty
    ): Int
}


