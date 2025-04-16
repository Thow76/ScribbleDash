
package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scribbledash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    navController: NavController,
    viewModel: DrawViewModel = hiltViewModel()
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
                onUndo = { viewModel.onAction(DrawingAction.OnUndo) },
                onRedo = { viewModel.onAction(DrawingAction.OnRedo) },
                onClearCanvas = { viewModel.onAction(DrawingAction.OnClearCanvasClick) },
                isUndoEnabled = uiState.canUndo,
                isRedoEnabled = uiState.canRedo,
                isClearEnabled = uiState.hasPaths
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                // Pass absolute position where drag started
                                viewModel.onAction(DrawingAction.OnNewPathStart)
                            },
                            onDrag = { change, _ ->
                                // Use the absolute position from the pointer event
                                val position = change.position
                                viewModel.onAction(DrawingAction.OnDraw(position))
                            },
                            onDragEnd = {
                                viewModel.onAction(DrawingAction.OnPathEnd)
                            }
                        )
                    }
            ) {
                // Grid Canvas
//                Canvas(modifier = Modifier.fillMaxSize()) {
//                    val step = size.width / 3f
//                    for (i in 1..2) {
//                        // Grid lines are commented out, but keeping the loop as a placeholder
//                    }
//
//                    // Draw saved paths
//                    uiState.paths.forEach { pathData ->
//                        drawPath(
//                            path = pathData.path,
//                            color = pathData.color,
//                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f)
//                        )
//                    }
//
//                    // Draw current path
//                    uiState.currentPathData?.let { currentPath ->
//                        drawPath(
//                            path = currentPath.path,
//                            color = currentPath.color,
//                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f)
//                        )
//                    }
//                }
                // Grid Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val step = size.width / 3f
                    for (i in 1..2) {
                        // Grid lines are commented out, but keeping the loop as a placeholder
                    }

                    // Draw only the visible paths
                    uiState.paths.take(uiState.visiblePathCount).forEach { pathData ->
                        drawPath(
                            path = pathData.path,
                            color = pathData.color,
                            style = Stroke(width = 5f)
                        )
                    }

                    // Draw current path
                    uiState.currentPathData?.let { currentPath ->
                        drawPath(
                            path = currentPath.path,
                            color = currentPath.color,
                            style = Stroke(width = 5f)
                        )
                    }
                }
            }
        }
    }
}


