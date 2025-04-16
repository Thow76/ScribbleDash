package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DrawViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val MAX_UNDO_REDO = 10 // Increased limit, can be made configurable
    }

    private val _state = MutableStateFlow(DrawingState())
    val state: StateFlow<DrawingState> = _state.asStateFlow()

    private val undoStack = ArrayDeque<PathData>()
    private val redoStack = ArrayDeque<PathData>()
    private var maxUndoRedo = MAX_UNDO_REDO

    fun setMaxUndoRedo(limit: Int) {
        maxUndoRedo = limit.coerceAtLeast(1) // Ensure a minimum value of 1
    }

    fun onAction(action: DrawingAction) {
        synchronized(this) {
            when (action) {
                is DrawingAction.OnSelectColor -> onSelectColor(action.color)
                DrawingAction.OnNewPathStart -> onNewPathStart()
                is DrawingAction.OnDraw -> onDraw(action.offset)
                DrawingAction.OnPathEnd -> onPathEnd()
                DrawingAction.OnClearCanvasClick -> onClearCanvas()
                DrawingAction.OnUndo -> onUndo()           // Add this
                DrawingAction.OnRedo -> onRedo()
            }
        }
    }

    private fun onSelectColor(color: Color) {
        _state.update { it.copy(selectedColor = color) }
    }

    private fun onNewPathStart() {
        _state.update { state ->
            state.copy(
                currentPathData = PathData(
                    color = state.selectedColor,
                    path = Path(),
                    points = emptyList()
                )
            )
        }
    }

    private fun onDraw(offset: Offset) {
        val current = _state.value.currentPathData ?: return

        val newPoints = current.points + offset
        val updatedPath = Path().apply {
            if (newPoints.isNotEmpty()) {
                moveTo(newPoints.first().x, newPoints.first().y)
                newPoints.drop(1).forEach {
                    lineTo(it.x, it.y)
                }
            }
        }

        _state.update {
            it.copy(
                currentPathData = current.copy(
                    points = newPoints,
                    path = updatedPath
                )
            )
        }
    }

//    private fun onPathEnd() {
//        val finishedPath = _state.value.currentPathData
//        if (finishedPath == null || finishedPath.points.isEmpty()) {
//            return // Avoid adding empty paths
//        }
//
//        undoStack.addLast(finishedPath)
//        if (undoStack.size > maxUndoRedo) undoStack.removeFirst()
//        redoStack.clear() // Clear redo stack on new action
//
//        _state.update {
//            it.copy(
//                paths = it.paths + finishedPath,
//                currentPathData = null,
//                canUndo = undoStack.isNotEmpty(),
//                canRedo = redoStack.isNotEmpty()
//            )
//        }
//    }

    private fun onPathEnd() {
        val finishedPath = _state.value.currentPathData ?: return

        undoStack.addLast(finishedPath)
        if (undoStack.size > MAX_UNDO_REDO) undoStack.removeFirst()
        redoStack.clear()

        _state.update { state ->
            val newPaths = state.paths + finishedPath
            state.copy(
                paths = newPaths,                     // Add the completed path to the persistent list
                currentPathData = null,               // Clear current path
                visiblePathCount = newPaths.size,     // CRITICAL: Set the visible count to include ALL paths
                canUndo = true,
                canRedo = false,
                hasPaths = true
            )
        }
    }

//    private fun onClearCanvas() {
//        undoStack.clear()
//        redoStack.clear()
//
//        _state.update {
//            it.copy(
//                paths = emptyList(),
//                currentPathData = null,
//                canUndo = true,
//                canRedo = false
//            )
//        }
//    }

    private fun onClearCanvas() {
        undoStack.clear()
        redoStack.clear()
        _state.update {
            it.copy(
                paths = emptyList(),
                currentPathData = null,
                canUndo = false,
                canRedo = false,
                hasPaths = false
            )
        }
    }

//    fun undo() {
//        if (undoStack.isNotEmpty()) {
//            val last = undoStack.removeLast()
//            redoStack.addLast(last)
//            if (redoStack.size > maxUndoRedo) redoStack.removeFirst()
//
//            _state.update {
//                it.copy(
//                    paths = it.paths - last,
//                    canUndo = undoStack.isNotEmpty(),
//                    canRedo = redoStack.isNotEmpty()
//                )
//            }
//        }
//    }

    fun onUndo() {
        if (undoStack.isNotEmpty()) {
            val last = undoStack.removeLast()
            redoStack.addLast(last)

            _state.update {
                it.copy(
                    // Don't change the paths list, just track how many are visible
                    visiblePathCount = it.visiblePathCount - 1,
                    canUndo = undoStack.isNotEmpty(),
                    canRedo = true,
                    hasPaths = it.visiblePathCount > 1
                )
            }
        }
    }

//    fun redo() {
//        if (redoStack.isNotEmpty()) {
//            val restored = redoStack.removeLast()
//            undoStack.addLast(restored)
//            if (undoStack.size > maxUndoRedo) undoStack.removeFirst()
//
//            _state.update {
//                it.copy(
//                    paths = it.paths + restored,
//                    canUndo = undoStack.isNotEmpty(),
//                    canRedo = redoStack.isNotEmpty()
//                )
//            }
//        }
//    }


    private fun onRedo() {
        if (redoStack.isNotEmpty()) {
            val restored = redoStack.removeLast()
            undoStack.addLast(restored)
            if (undoStack.size > MAX_UNDO_REDO) undoStack.removeFirst()

            _state.update {
                it.copy(
                    paths = it.paths + restored,
                    canUndo = true,
                    canRedo = redoStack.isNotEmpty(),
                    hasPaths = true
                )
            }
        }
    }

    fun canUndo(): Boolean = undoStack.isNotEmpty()
    fun canRedo(): Boolean = redoStack.isNotEmpty()
    fun hasPaths(): Boolean = _state.value.paths.isNotEmpty()
}


