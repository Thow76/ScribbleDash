package com.example.scribbledash.features.drawscreen.presentation.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty

sealed interface DrawingActionUiEvent {

    data class OnStartGame(val difficulty: Difficulty) : DrawingActionUiEvent
    object OnTick : DrawingActionUiEvent
    object OnDoneClick : DrawingActionUiEvent
    object OnRetryClick : DrawingActionUiEvent

    data class OnNewPathStart(val offset: Offset): DrawingActionUiEvent
    data class OnDraw(val offset: Offset): DrawingActionUiEvent
    data object OnPathEnd: DrawingActionUiEvent
    data class OnSelectColor(val color: Color): DrawingActionUiEvent
    data class OnStrokeWidthChange(val width: Float): DrawingActionUiEvent
    data object OnUndoClick: DrawingActionUiEvent
    data object OnRedoClick: DrawingActionUiEvent
    data object OnClearCanvasClick: DrawingActionUiEvent
}