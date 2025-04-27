package com.example.scribbledash.features.drawscreen.presentation.state

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

//sealed interface DrawingAction {
//    /** Triggered when a new drawing stroke begins (on touch down) */
//    data object OnNewPathStart : DrawingAction
//
//    /** Tracks the current position while drawing (during drag motion) */
//    data class OnDraw(val offset: Offset) : DrawingAction
//
//    /** Triggered when the current drawing stroke is completed (on touch up) */
//    data object OnPathEnd : DrawingAction
//
//    /** Changes the active drawing color */
//    data class OnSelectColor(val color: Color) : DrawingAction
//
//    /** Removes all paths from the canvas */
//    data object OnClearCanvasClick : DrawingAction
//
//    /** Removes the most recent path from view */
//    data object OnUndo : DrawingAction
//
//    /** Restores the most recently undone path */
//    data object OnRedo : DrawingAction
//}

sealed interface DrawingAction {
    data class OnNewPathStart(val offset: Offset): DrawingAction
    data class OnDraw(val offset: Offset): DrawingAction
    data object OnPathEnd: DrawingAction
    data class OnSelectColor(val color: Color): DrawingAction
    data class OnStrokeWidthChange(val width: Float): DrawingAction
    data object OnUndoClick: DrawingAction
    data object OnRedoClick: DrawingAction
    data object OnClearCanvasClick: DrawingAction
}