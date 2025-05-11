package com.example.scribbledash.features.drawscreen.presentation.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData
import com.example.scribbledash.features.drawscreen.state.GameState

data class DrawingState(
    val gameState: GameState      = GameState.Preview,
    val countdown: Int            = 3,
    val svgName: String?          = null, // ← new
    val difficulty: Difficulty    = Difficulty.Beginner,
    val targetPaths: List<List<Offset>> = emptyList(),
    val paths:        List<StrokeData>   = emptyList(),
    val undonePaths:  List<StrokeData>   = emptyList(),
    val currentPath:  StrokeData?        = null,
    val selectedColor: Color = Color.Black,
    val thickness:     Float = 5f,
    val score:         Int?   = null
)



