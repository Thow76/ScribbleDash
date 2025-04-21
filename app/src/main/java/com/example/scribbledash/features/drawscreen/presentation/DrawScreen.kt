
package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scribbledash.features.drawscreen.presentation.components.DrawBottomActions
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingAction
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.take

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    navController: NavController,
    viewModel: DrawingViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                title = {}
            )
        },
        bottomBar = {
            DrawBottomActions(
                onUndo = { viewModel.onAction(DrawingAction.OnUndoClick) },
                onRedo = { viewModel.onAction(DrawingAction.OnRedoClick) },
                onClear = { viewModel.onAction(DrawingAction.OnClearCanvasClick) },
                canUndo = uiState.paths.isNotEmpty(),
                canRedo = uiState.undonePaths.isNotEmpty(),
                canClear = uiState.paths.isNotEmpty()

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            DrawingCanvas(
            modifier = Modifier
                .fillMaxSize(),
            paths = uiState.paths,
            currentPath = uiState.currentPath,
            onDrawStart = { offset ->
                viewModel.onAction(DrawingAction.OnNewPathStart(offset))
            },
            onDrawMove = { offset ->
                viewModel.onAction(DrawingAction.OnDraw(offset))
            },
            onDrawEnd = {
                viewModel.onAction(DrawingAction.OnPathEnd)
            }
            )
        }
    }
}




