package com.example.scribbledash.features.difficultyscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

@Composable
fun DifficultySelectionScreen(
    navController: NavController,
    viewModel: DifficultySelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is DifficultySelectionViewModel.UiEvent.NavigateBack -> navController.popBackStack()
                is DifficultySelectionViewModel.UiEvent.NavigateToDraw -> navController.navigate("draw/${event.difficulty.name}")
            }
        }
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { viewModel.onEvent(DifficultySelectionEvent.Close) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFEFAF6),
                            Color(0xFFFFF1E2)
                        )
                    )
                )
                .padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 64.dp)
            ) {
                Text(
                    text = "Start drawing!",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color(0xFF514437)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose a difficulty setting",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF7F7163)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    uiState.options.forEachIndexed { index, difficulty ->
                        val yOffset = when (index) {
                            0 -> (-8).dp
                            1 -> 8.dp
                            else -> (-4).dp
                        }
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .offset(y = yOffset)
                                .clickable { viewModel.onEvent(DifficultySelectionEvent.SelectDifficulty(difficulty)) },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, Color(0xFF0DD280)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.height(120.dp)
                            ) {
                                Text(
                                    text = difficulty.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF514437)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
