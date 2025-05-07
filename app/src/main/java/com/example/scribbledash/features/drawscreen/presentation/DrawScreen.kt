
package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scribbledash.R
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.drawscreen.presentation.components.DrawBottomActions
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent
import com.example.scribbledash.features.drawscreen.viewmodel.DrawingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    navController: NavController,
    difficulty: Difficulty,
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
                            painter = painterResource(R.drawable.close_circle_icon),
                            contentDescription = "Redo",
                            modifier = Modifier
                                .size(28.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

                        )
                    }
                },
                title = {}
            )
        },
        bottomBar = {
            DrawBottomActions(
                onUndo = { viewModel.onAction(DrawingActionUiEvent.OnUndoClick) },
                onRedo = { viewModel.onAction(DrawingActionUiEvent.OnRedoClick) },
                onClear = { viewModel.onAction(DrawingActionUiEvent.OnClearCanvasClick) },
                canUndo = uiState.paths.isNotEmpty(),
                canRedo = uiState.undonePaths.isNotEmpty(),
                canClear = uiState.paths.isNotEmpty()

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Time to Draw!",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
                DrawingCanvas(
                    modifier = Modifier
                        .fillMaxWidth(),
                    paths = uiState.paths,
                    currentPath = uiState.currentPath,
                    onDrawStart = { offset ->
                        viewModel.onAction(DrawingActionUiEvent.OnNewPathStart(offset))
                    },
                    onDrawMove = { offset ->
                        viewModel.onAction(DrawingActionUiEvent.OnDraw(offset))
                    },
                    onDrawEnd = {
                        viewModel.onAction(DrawingActionUiEvent.OnPathEnd)
                    }
                )
            }
        }
    }





