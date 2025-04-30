package com.example.scribbledash.features.homescreen.state

// Define the events that can be triggered from the UI
sealed class HomeUiEvent {
    object OnGameModeClicked : HomeUiEvent()
}