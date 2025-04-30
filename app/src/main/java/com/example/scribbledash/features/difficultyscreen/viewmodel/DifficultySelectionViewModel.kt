package com.example.scribbledash.features.difficultyscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.difficultyscreen.state.DifficultySelectionUiEvent
import com.example.scribbledash.features.difficultyscreen.state.DifficultySelectionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DifficultySelectionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DifficultySelectionUiState())
    val uiState: StateFlow<DifficultySelectionUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun onEvent(event: DifficultySelectionUiEvent) {
        when (event) {
            is DifficultySelectionUiEvent.Close -> viewModelScope.launch {
                _eventFlow.emit(UiEvent.NavigateBack)
            }
            is DifficultySelectionUiEvent.SelectDifficulty -> viewModelScope.launch {
                _eventFlow.emit(UiEvent.NavigateToDraw(event.difficulty))
            }
        }
    }

    sealed class UiEvent {
        object NavigateBack : UiEvent()
        data class NavigateToDraw(val difficulty: Difficulty) : UiEvent()
    }
}