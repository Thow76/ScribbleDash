package com.example.scribbledash.features.drawscreen.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.difficultyscreen.domain.drawing.usecase.DrawingComparer
//import com.example.scribbledash.features.drawscreen.presentation.model.PathData
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent
//import com.example.scribbledash.features.drawscreen.presentation.state.DrawingState
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingState
import com.example.scribbledash.features.drawscreen.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.plus

//@HiltViewModel
//class DrawingViewModel @Inject constructor(
//    private val repository: DrawingsRepositoryInterface,
//    private val comparer: DrawingComparer
//): ViewModel() {
//
//    private val _state = MutableStateFlow(DrawingState())
//    val state: StateFlow<DrawingState> = _state.asStateFlow()
//
//    fun onAction(action: DrawingActionUiEvent) {
//        when (action) {
//            is DrawingActionUiEvent.OnStartGame       -> initGame(action.difficulty)
//            // DrawingActionUiEvent.OnTick               -> tick()
//            DrawingActionUiEvent.OnDoneClick          -> finishDrawing()
//            DrawingActionUiEvent.OnRetryClick         -> initGame(_state.value)
//            //DrawingActionUiEvent.OnRetryClick         -> initGame(action.difficulty)
//            is DrawingActionUiEvent.OnNewPathStart -> handleDrawStart(action.offset)
//            is DrawingActionUiEvent.OnDraw -> handleDrawMove(action.offset)
//            is DrawingActionUiEvent.OnPathEnd -> handleDrawEnd()
//            is DrawingActionUiEvent.OnSelectColor -> handleColorSelection(action.color)
//            is DrawingActionUiEvent.OnStrokeWidthChange -> handleStrokeWidthChange(action.width)
//            is DrawingActionUiEvent.OnUndoClick -> handleUndo()
//            is DrawingActionUiEvent.OnRedoClick -> handleRedo()
//            is DrawingActionUiEvent.OnClearCanvasClick -> handleClear()
//
//        }
//    }
//
//
//
//    private fun initGame(stateSnapshot: DrawingState) {
//        initGame(stateSnapshot.difficulty)  // now `difficulty` exists
//    }
//
//        private fun initGame(difficulty: Difficulty) {
//            viewModelScope.launch {
//                // 1) pick a random SVG
//                val svgName = repository.getRandomDrawing()       // now returns “foo.svg”
//
//                // 2) still load paths for smoothing/scoring (Option 2)
//                val target   = repository.loadPaths(svgName)      // unchanged parsing logic
//
//                // 3) emit Preview state with both svgName & targetPaths
//                _state.update {
//                    DrawingState(
//                        gameState   = GameState.Preview,
//                        countdown   = 3,
//                        svgName     = svgName,
//                        difficulty  = difficulty,
//                        targetPaths = target
//                    )
//                }
//
//                // countdown…
//                for (i in 3 downTo 1) {
//                    _state.update { it.copy(countdown = i) }
//                    delay(1_000)
//                }
//                _state.update { it.copy(gameState = GameState.Drawing) }
//            }
//        }
//
//    private fun tick() {
//        // unused if using the loop above
//    }
//
//    private fun finishDrawing() {
//        // TODO: compute actual similarity
//        val score = 0
//        _state.update { it.copy(gameState = GameState.Result, score = score) }
//    }
//
//    private fun handleDrawStart(offset: Offset) {
//        val newPath = StrokeData(
//            points = listOf(offset),
//            color = _state.value.selectedColor,
//            strokeWidth = _state.value.thickness
//        )
//        _state.update { it.copy(currentPath = newPath) }
//    }
//
//    private fun handleDrawMove(offset: Offset) {
//        _state.value.currentPath?.let { currentPath ->
//            _state.update {
//                it.copy(
//                    currentPath = currentPath.copy(
//                        points = currentPath.points + offset
//                    )
//                )
//            }
//        }
//    }
//
//    private fun handleDrawEnd() {
//        val currentPath = _state.value.currentPath
//        if (currentPath != null) {
//            _state.update {
//                it.copy(
//                    paths = it.paths + currentPath,
//                    currentPath = null,
//                    undonePaths = emptyList()
//                )
//            }
//        }
//    }
//
//    private fun handleUndo() {
//        _state.update { currentState ->
//            if (currentState.paths.isNotEmpty()) {
//                val lastPath = currentState.paths.last()
//                currentState.copy(
//                    paths = currentState.paths.dropLast(1),
//                    undonePaths = currentState.undonePaths + lastPath
//                )
//            } else {
//                currentState
//            }
//        }
//    }
//
//    private fun handleRedo() {
//        _state.update { currentState ->
//            if (currentState.undonePaths.isNotEmpty()) {
//                val pathToRestore = currentState.undonePaths.last()
//                currentState.copy(
//                    paths = currentState.paths + pathToRestore,
//                    undonePaths = currentState.undonePaths.dropLast(1)
//                )
//            } else {
//                currentState
//            }
//        }
//    }
//
//    private fun handleClear() {
//        _state.update { it.copy(paths = emptyList(), undonePaths = emptyList()) }
//    }
//
//    private fun handleColorSelection(color: Color) {
//        _state.update { it.copy(selectedColor = color) }
//    }
//
//    private fun handleStrokeWidthChange(width: Float) {
//        _state.update { it.copy(thickness = width) }
//    }
//}


@HiltViewModel
class DrawingViewModel @Inject constructor(
    private val repository: DrawingsRepositoryInterface,
    private val comparer:   DrawingComparer
) : ViewModel() {

    private val _state = MutableStateFlow(DrawingState())
    val state: StateFlow<DrawingState> = _state.asStateFlow()

    fun onAction(action: DrawingActionUiEvent) {
        when (action) {
            is DrawingActionUiEvent.OnStartGame ->
                initGame(action.difficulty)

            DrawingActionUiEvent.OnUndoClick ->
                handleUndo()

            DrawingActionUiEvent.OnRedoClick ->
                handleRedo()

            DrawingActionUiEvent.OnClearCanvasClick ->
                handleClear()

            DrawingActionUiEvent.OnDoneClick ->
                finishDrawing()

            is DrawingActionUiEvent.OnNewPathStart ->
                handleDrawStart(action.offset)

            is DrawingActionUiEvent.OnDraw ->
                handleDrawMove(action.offset)

            DrawingActionUiEvent.OnPathEnd ->
                handleDrawEnd()

            is DrawingActionUiEvent.OnSelectColor ->
                handleColorSelection(action.color)

            is DrawingActionUiEvent.OnStrokeWidthChange ->
                handleStrokeWidthChange(action.width)

            is DrawingActionUiEvent.OnRetryClick ->
                // retry with the same difficulty
                initGame(_state.value.difficulty)
        }
    }

    private fun initGame(difficulty: Difficulty) {
        viewModelScope.launch {
            // 1) pick a random drawing asset
            val name   = repository.getRandomDrawing()        // e.g. "apple.svg"
            // 2) parse it into stroke-points
            val target = repository.loadPaths(name)
            val rawPathData = repository.loadRawPathData(name)

            // 3) emit Preview state
            _state.update {
                DrawingState(
                    gameState   = GameState.Preview,
                    countdown   = 3,
                    svgName     = name,
                    difficulty  = difficulty,
                    targetPaths = target,
                    targetPathData = rawPathData
                )
            }

            // 4) run 3→1 countdown
            for (i in 3 downTo 1) {
                delay(1_000)
                _state.update { it.copy(countdown = i) }
            }

            // 5) switch to Drawing
            _state.update { it.copy(gameState = GameState.Drawing) }
        }
    }
    private fun finishDrawing() {
        val userPaths   = _state.value.paths.map { it.points }
        val exampleData = _state.value.targetPathData    // List<String>
        val userSW      = _state.value.thickness
        val difficulty  = _state.value.difficulty

        val score = comparer.compare(
            userPaths       = userPaths,
            exampleData     = exampleData,
            userStrokeWidth = userSW,
            difficulty      = difficulty
        )

        _state.update { it.copy(gameState = GameState.Result, score = score) }
    }

    private fun handleDrawStart(offset: Offset) {
        val newPath = StrokeData(
            points      = listOf(offset),
            color       = _state.value.selectedColor,
            strokeWidth = _state.value.thickness
        )
        _state.update { it.copy(currentPath = newPath) }
    }

    private fun handleDrawMove(offset: Offset) {
        _state.value.currentPath?.let { current ->
            _state.update {
                it.copy(currentPath = current.copy(points = current.points + offset))
            }
        }
    }

    private fun handleDrawEnd() {
        _state.value.currentPath?.let { finished ->
            _state.update {
                it.copy(
                    paths        = it.paths + finished,
                    currentPath  = null,
                    undonePaths  = emptyList()
                )
            }
        }
    }

    private fun handleUndo() {
        _state.update { s ->
            if (s.paths.isEmpty()) return@update s
            val last = s.paths.last()
            s.copy(
                paths       = s.paths.dropLast(1),
                undonePaths = s.undonePaths + last
            )
        }
    }

    private fun handleRedo() {
        _state.update { s ->
            if (s.undonePaths.isEmpty()) return@update s
            val toRestore = s.undonePaths.last()
            s.copy(
                paths       = s.paths + toRestore,
                undonePaths = s.undonePaths.dropLast(1)
            )
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




