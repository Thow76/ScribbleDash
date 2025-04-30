package com.example.scribbledash.features.difficultyscreen.state

import com.example.scribbledash.features.difficultyscreen.domain.Difficulty

data class DifficultySelectionUiState(
    val options: List<Difficulty> = Difficulty.values().toList()
)