package com.example.scribbledash.features.homescreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scribbledash.features.homescreen.state.HomeUiEvent
import com.example.scribbledash.features.homescreen.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    // StateFlow representing the UI state of HomeScreen
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    // Handle incoming events in a UDF manner
    fun handleEvent(event: HomeUiEvent) {
        viewModelScope.launch {
            when (event) {
                HomeUiEvent.OnGameModeClicked -> {
                    // Trigger navigation, or set a flag that triggers navigation
                    // in your composable, etc.
                }
            }
        }
    }
}