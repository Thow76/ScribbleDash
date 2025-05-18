package com.example.scribbledash.features.drawscreen.presentation

import android.graphics.Rect
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.example.scribbledash.features.drawscreen.presentation.state.DrawingState
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent
import com.example.scribbledash.features.drawscreen.viewmodel.DrawingViewModel
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas


@Composable
fun DrawScreenContent(
    uiState: DrawingState,
    viewModel: DrawingViewModel
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Time to Draw!", style = MaterialTheme.typography.displayMedium)
        DrawingCanvas(
            modifier = Modifier.fillMaxWidth(),
            paths = uiState.paths,
            currentPath = uiState.currentPath,
            autoFit = false,
            onDrawStart = { viewModel.onAction(DrawingActionUiEvent.OnNewPathStart(it)) },
            onDrawMove  = { viewModel.onAction(DrawingActionUiEvent.OnDraw(it)) },
            onDrawEnd   = { viewModel.onAction(DrawingActionUiEvent.OnPathEnd) }
        )
        Text("Your Drawing", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
    }
}