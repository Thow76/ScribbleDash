package com.example.scribbledash.features.drawscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scribbledash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
        navController: NavController,
//    onClose: () -> Unit,
//    onUndo: () -> Unit,
//    onRedo: () -> Unit,
//    onClearCanvas: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                actions = {
                    // Close button at top right
                    IconButton(onClick = {
                    //    onClose()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Close"
                        )
                    }
                },
                title = { /* Could put "Draw Screen" or be empty if desired */ }
            )
        },
        bottomBar = {
//            DrawBottomActions(
//                onUndo = onUndo,
//                onRedo = onRedo,
//                onClearCanvas = onClearCanvas,
//                isUndoEnabled = true, // TODO: bind to real state
//                isRedoEnabled = false, // TODO: bind to real state
//                isClearEnabled = true // TODO: bind to real state
//            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Center the actual drawing canvas
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .aspectRatio(1f) // ensures 1:1 ratio
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                // TODO: Implement your actual drawing logic here
                // For example, a Canvas composable with drawScope
                // or a custom layout that handles pointer events
                // to draw lines or shapes.

                // Example of drawing grid lines with Canvas:
                /*
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val step = size.width / 3f
                    for (i in 1..2) {
                        // Vertical line
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(x = i * step, y = 0f),
                            end = Offset(x = i * step, y = size.height),
                            strokeWidth = 2f
                        )
                        // Horizontal line
                        drawLine(
                            color = Color.LightGray,
                            start = Offset(x = 0f, y = i * step),
                            end = Offset(x = size.width, y = i * step),
                            strokeWidth = 2f
                        )
                    }
                }
                */
            }
        }
    }
}

@Composable
fun DrawBottomActions(
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClearCanvas: () -> Unit,
    isUndoEnabled: Boolean,
    isRedoEnabled: Boolean,
    isClearEnabled: Boolean
) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = onUndo,
            icon = {
                // Undo Icon
                Icon(
                    painter = painterResource(id = /* e.g. R.drawable.ic_undo */ 0),
                    contentDescription = "Undo"
                )
            },
            enabled = isUndoEnabled,
            label = { Text("Undo") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onRedo,
            icon = {
                // Redo Icon
                Icon(
                    painter = painterResource(id = /* e.g. R.drawable.ic_redo */ 0),
                    contentDescription = "Redo"
                )
            },
            enabled = isRedoEnabled,
            label = { Text("Redo") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onClearCanvas,
            icon = {
                // Clear Icon
                Icon(
                    painter = painterResource(id = /* e.g. R.drawable.ic_clear */ 0),
                    contentDescription = "Clear Canvas"
                )
            },
            enabled = isClearEnabled,
            label = { Text("Clear") }
        )
    }
}
