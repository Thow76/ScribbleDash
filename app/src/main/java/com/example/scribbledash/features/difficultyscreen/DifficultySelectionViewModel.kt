package com.example.scribbledash.features.difficultyscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DifficultySelectionViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(DifficultySelectionUiState())
    val uiState: StateFlow<DifficultySelectionUiState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun onEvent(event: DifficultySelectionEvent) {
        when (event) {
            is DifficultySelectionEvent.Close -> viewModelScope.launch {
                _eventFlow.emit(UiEvent.NavigateBack)
            }
            is DifficultySelectionEvent.SelectDifficulty -> viewModelScope.launch {
                _eventFlow.emit(UiEvent.NavigateToDraw(event.difficulty))
            }
        }
    }

    sealed class UiEvent {
        object NavigateBack : UiEvent()
        data class NavigateToDraw(val difficulty: Difficulty) : UiEvent()
    }
}