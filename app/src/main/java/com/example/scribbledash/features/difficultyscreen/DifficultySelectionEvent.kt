package com.example.scribbledash.features.difficultyscreen

sealed class DifficultySelectionEvent {
    object Close : DifficultySelectionEvent()
    data class SelectDifficulty(val difficulty: Difficulty) : DifficultySelectionEvent()
}