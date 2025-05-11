
package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent
import com.example.scribbledash.features.drawscreen.state.GameState
import com.example.scribbledash.features.drawscreen.viewmodel.DrawingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    navController: NavController,
    difficulty: Difficulty,
    viewModel: DrawingViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val initialDifficulty = remember(difficulty) { difficulty }

    LaunchedEffect(difficulty) {
        viewModel.onAction(DrawingActionUiEvent.OnStartGame(difficulty))
    }

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
                onDone = { viewModel.onAction(DrawingActionUiEvent.OnDoneClick) },
                canUndo = uiState.paths.isNotEmpty(),
                canRedo = uiState.undonePaths.isNotEmpty(),
                canClear = uiState.paths.isNotEmpty(),
                showDone = true
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            when (uiState.gameState) {

                GameState.Preview -> {
                    val svgAsset = uiState.svgName
                        ?: return@Box  // or show a fallback
                    DrawScreenPreview(svgAssetName = svgAsset, uiState.countdown)}
                GameState.Drawing -> DrawScreenContent(uiState, viewModel)
                GameState.Result  -> DrawScreenResult(uiState.score ?: 0) {
                    viewModel.onAction(DrawingActionUiEvent.OnRetryClick)
                }
            }
        }
    }
}





