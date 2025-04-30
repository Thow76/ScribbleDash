package com.example.scribbledash.features.drawscreen.presentation.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed interface DrawingActionUiEvent {
    data class OnNewPathStart(val offset: Offset): DrawingActionUiEvent
    data class OnDraw(val offset: Offset): DrawingActionUiEvent
    data object OnPathEnd: DrawingActionUiEvent
    data class OnSelectColor(val color: Color): DrawingActionUiEvent
    data class OnStrokeWidthChange(val width: Float): DrawingActionUiEvent
    data object OnUndoClick: DrawingActionUiEvent
    data object OnRedoClick: DrawingActionUiEvent
    data object OnClearCanvasClick: DrawingActionUiEvent
}