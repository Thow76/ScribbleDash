package com.example.scribbledash.features.difficultyscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.scribbledash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyScreen(
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
                    .padding(16.dp)
                    .fillMaxSize()
                    .padding(top = 120.dp)
            ) {
                Text(
                    text = "Start drawing!",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color(0xFF514437)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Choose a difficulty setting",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF7F7163)
                )
                Spacer(modifier = Modifier.height(56.dp))
                Row{
                    // Iterate through each difficulty option with its index
                    uiState.options.forEachIndexed { index, difficulty ->
                        // Map each difficulty name to its corresponding SVG resource
                        // These SVG files should be located in the res/drawable directory
                        val resourceId = when (difficulty.name.lowercase()) {
                            "beginner" -> R.drawable.pencil
                            "challenging" -> R.drawable.paintbrush
                            "master" -> R.drawable.master
                            else -> R.drawable.beginner // Default to beginner if name doesn't match
                        }

                        // Apply vertical offset to each difficulty card for visual staggering effect
                        // First item (index 0) moves down, middle item (index 1) moves up significantly
                        val yOffset = when (index) {
                            0 -> 4.dp
                            1 -> (-15).dp
                            2 -> 4.dp
                            else -> (8).dp
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .offset(y = yOffset)
                                .clickable {
                                    viewModel.onEvent(
                                        DifficultySelectionEvent.SelectDifficulty(
                                            difficulty
                                        )
                                    )
                                }
                        ) {

                            Surface(
                                modifier = Modifier.size(90.dp),
                                shape = CircleShape,
                                shadowElevation = 3.dp,
                                color = Color.White
                            ) {
                                // Get custom alignment based on difficulty
                                val alignment = when (difficulty.name.lowercase()) {
                                    "beginner" -> Alignment.TopEnd
                                    "challenging" -> Alignment.BottomCenter
                                    "master" -> Alignment.Center
                                    else -> Alignment.Center
                                }

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = alignment
                                ) {
                                    Image(
                                        painter = painterResource(id = resourceId),
                                        contentDescription = difficulty.title,
                                        contentScale = ContentScale.None,
                                    )
                                }

                            }
                            Text(
                                    modifier = Modifier.offset(y = (12).dp),
                            text = difficulty.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF514437)
                            )
                        }
                    }


                }
            }

        }

    }}




