package com.example.scribbledash.features.oneroundwonder.state

import androidx.compose.ui.geometry.Offset
import com.example.scribbledash.features.oneroundwonder.domain.EvaluationResult

// --- 1. State definition ---
sealed class OneRoundState {
    /** Shown while picking the example and/or running the comparison */
    object Loading : OneRoundState()

    data class Preview(
        val examplePath: String,
        val secondsLeft: Int
    ) : OneRoundState()

    /** User is drawing; paths holds the strokes, canSubmit toggles “Done” */
    data class Drawing(
        val paths: List<List<Offset>>,
        val canSubmit: Boolean
    ) : OneRoundState()

    /** Shows the final score, rating, and bitmaps */
    data class Result(
        val result: EvaluationResult
    ) : OneRoundState()

    /** Any error during loading or evaluation */
    data class Error(
        val message: String
    ) : OneRoundState()
}

