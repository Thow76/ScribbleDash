package com.example.scribbledash.features.difficultyscreen.state

import com.example.scribbledash.features.difficultyscreen.domain.Difficulty

sealed class DifficultySelectionUiEvent {
    object Close : DifficultySelectionUiEvent()
    data class SelectDifficulty(val difficulty: Difficulty) : DifficultySelectionUiEvent()
}