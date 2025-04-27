package com.example.scribbledash.features.drawscreen.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
//import com.example.scribbledash.features.drawscreen.presentation.model.PathData
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingAction
//import com.example.scribbledash.features.drawscreen.presentation.state.DrawingState
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.plus

@HiltViewModel
class DrawingViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(DrawingViewState())
    val state: StateFlow<DrawingViewState> = _state.asStateFlow()

    fun onAction(action: DrawingAction) {
        when (action) {
            is DrawingAction.OnNewPathStart -> handleDrawStart(action.offset)
            is DrawingAction.OnDraw -> handleDrawMove(action.offset)
            is DrawingAction.OnPathEnd -> handleDrawEnd()
            is DrawingAction.OnSelectColor -> handleColorSelection(action.color)
            is DrawingAction.OnStrokeWidthChange -> handleStrokeWidthChange(action.width)
            is DrawingAction.OnUndoClick -> handleUndo()
            is DrawingAction.OnRedoClick -> handleRedo()
            is DrawingAction.OnClearCanvasClick -> handleClear()
        }
    }

    private fun handleDrawStart(offset: Offset) {
        val newPath = StrokeData(
            points = listOf(offset),
            color = _state.value.selectedColor,
            strokeWidth = _state.value.thickness
        )
        _state.update { it.copy(currentPath = newPath) }
    }

    private fun handleDrawMove(offset: Offset) {
        _state.value.currentPath?.let { currentPath ->
            _state.update {
                it.copy(
                    currentPath = currentPath.copy(
                        points = currentPath.points + offset
                    )
                )
            }
        }
    }

    private fun handleDrawEnd() {
        val currentPath = _state.value.currentPath
        if (currentPath != null) {
            _state.update {
                it.copy(
                    paths = it.paths + currentPath,
                    currentPath = null,
                    undonePaths = emptyList()
                )
            }
        }
    }

    private fun handleUndo() {
        _state.update { currentState ->
            if (currentState.paths.isNotEmpty()) {
                val lastPath = currentState.paths.last()
                currentState.copy(
                    paths = currentState.paths.dropLast(1),
                    undonePaths = currentState.undonePaths + lastPath
                )
            } else {
                currentState
            }
        }
    }

    private fun handleRedo() {
        _state.update { currentState ->
            if (currentState.undonePaths.isNotEmpty()) {
                val pathToRestore = currentState.undonePaths.last()
                currentState.copy(
                    paths = currentState.paths + pathToRestore,
                    undonePaths = currentState.undonePaths.dropLast(1)
                )
            } else {
                currentState
            }
        }
    }

    private fun handleClear() {
        _state.update { it.copy(paths = emptyList(), undonePaths = emptyList()) }
    }

    private fun handleColorSelection(color: Color) {
        _state.update { it.copy(selectedColor = color) }
    }

    private fun handleStrokeWidthChange(width: Float) {
        _state.update { it.copy(thickness = width) }
    }
}



