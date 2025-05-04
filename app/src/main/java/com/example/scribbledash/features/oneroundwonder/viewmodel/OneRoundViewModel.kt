package com.example.scribbledash.features.oneroundwonder.viewmodel

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.oneroundwonder.domain.EvaluateDrawingUseCase
import com.example.scribbledash.features.oneroundwonder.domain.LoadDrawingsUseCase
import com.example.scribbledash.features.oneroundwonder.state.OneRoundEvent
import com.example.scribbledash.features.oneroundwonder.state.OneRoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OneRoundViewModel @Inject constructor(
    private val loadDrawingsUseCase: LoadDrawingsUseCase,
    private val evaluateDrawingUseCase: EvaluateDrawingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // 1) Pull the difficulty out of the nav args
    private val difficulty: Difficulty = savedStateHandle
        .get<String>("difficulty")
        ?.let { Difficulty.valueOf(it) }
        ?: Difficulty.Beginner

    private val _uiState = MutableStateFlow<OneRoundState>(OneRoundState.Loading)
    val uiState: StateFlow<OneRoundState> = _uiState.asStateFlow()

    private var examplePath: String = ""
    private var userPaths: List<List<Offset>> = emptyList()

    init {
        // kick off the 3-second preview
        onEvent(OneRoundEvent.StartGame)
    }

    fun onEvent(event: OneRoundEvent) {
        when (event) {
            OneRoundEvent.StartGame -> startGame()
            OneRoundEvent.Tick      -> tick()
            is OneRoundEvent.PathRecorded -> recordPaths(event.paths)
            OneRoundEvent.Submit     -> submit()
            OneRoundEvent.Retry      -> startGame()
            OneRoundEvent.Back       -> { /* could emit a Back state if needed */ }
        }
    }

    private fun startGame() {
        examplePath = loadDrawingsUseCase.random()
        _uiState.value = OneRoundState.Preview(examplePath, secondsLeft = 3)
    }

    private fun tick() {
        val state = _uiState.value as? OneRoundState.Preview ?: return
        if (state.secondsLeft > 1) {
            _uiState.value = state.copy(secondsLeft = state.secondsLeft - 1)
        } else {
            // move to drawing mode
            _uiState.value = OneRoundState.Drawing(paths = emptyList(), canSubmit = false)
        }
    }

    private fun recordPaths(paths: List<List<Offset>>) {
        userPaths = paths
        _uiState.value = OneRoundState.Drawing(
            paths = paths,
            canSubmit = paths.isNotEmpty()
        )
    }

    private fun submit() {
        viewModelScope.launch {
            _uiState.value = OneRoundState.Loading      // optionally show a loader
            try {
                // 2) Pass the difficulty into your evaluate use-case
                val result = evaluateDrawingUseCase(
                    examplePath = examplePath,
                    userPaths   = userPaths,
                    difficulty  = difficulty
                )
                _uiState.value = OneRoundState.Result(result)
            } catch (t: Throwable) {
                _uiState.value = OneRoundState.Error(t.message ?: "Unknown error")
            }
        }
    }
}
