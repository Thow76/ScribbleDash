package com.example.scribbledash.features.oneroundwonder.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.scribbledash.features.drawscreen.presentation.components.DrawBottomActions
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnClearCanvasClick
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnDraw
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnNewPathStart
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnPathEnd
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnRedoClick
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent.OnUndoClick
import com.example.scribbledash.features.drawscreen.viewmodel.DrawingViewModel

@Composable
fun OneRoundDrawScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    viewModel: DrawingViewModel = hiltViewModel() // reuse canvas VM
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = { /* same close-icon bar */ },
        bottomBar = {
            DrawBottomActions(
                onUndo   = { viewModel.onAction(OnUndoClick) },
                onRedo   = { viewModel.onAction(OnRedoClick) },
                onClear  = { viewModel.onAction(OnClearCanvasClick) },
                canUndo  = uiState.paths.isNotEmpty(),
                canRedo  = uiState.undonePaths.isNotEmpty(),
                canClear = uiState.paths.isNotEmpty(),
                // **only here** we add the Done button
                showDone = true,
                onDone   = onSubmit
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            Text("Time to draw!", style = MaterialTheme.typography.displayMedium)
            DrawingCanvas(
                paths       = uiState.paths,
                currentPath = uiState.currentPath,
                onDrawStart = { viewModel.onAction(OnNewPathStart(it)) },
                onDrawMove  = { viewModel.onAction(OnDraw(it)) },
                onDrawEnd   = { viewModel.onAction(OnPathEnd) }
            )
        }
    }
}
