package com.example.scribbledash.features.homescreen.presentation

// Define the events that can be triggered from the UI
sealed class HomeUiEvent {
    object OnGameModeClicked : HomeUiEvent()
}