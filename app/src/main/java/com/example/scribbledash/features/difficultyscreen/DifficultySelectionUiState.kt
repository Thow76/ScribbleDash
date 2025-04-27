package com.example.scribbledash.features.difficultyscreen

data class DifficultySelectionUiState(
    val options: List<Difficulty> = Difficulty.values().toList()
)
