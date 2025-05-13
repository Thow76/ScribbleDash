package com.example.scribbledash.features.oneroundwonder.domain

import androidx.compose.ui.graphics.ImageBitmap

/** Holds the final score, rating text, and the two bitmaps to display */
data class EvaluationResult(
    val score: Int,
    val rating: String,
    val exampleBitmap: ImageBitmap,
    val userBitmap: ImageBitmap
)
