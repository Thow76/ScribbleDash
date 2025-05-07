package com.example.scribbledash.features.difficultyscreen.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import com.example.scribbledash.core.components.CustomTopAppBar
import com.example.scribbledash.core.components.GradientBackground
import com.example.scribbledash.core.components.ScreenHeader
import com.example.scribbledash.features.difficultyscreen.components.DifficultyOptions
import com.example.scribbledash.features.difficultyscreen.state.DifficultySelectionUiEvent
import com.example.scribbledash.features.difficultyscreen.viewmodel.DifficultySelectionViewModel
import com.example.scribbledash.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
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
                is DifficultySelectionViewModel.UiEvent.NavigateToDraw -> navController.navigate(("${Screen.Draw.route}/${event.difficulty.name}"))
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                onClose = { navController.popBackStack() }

            )
        }
    ) { innerPadding ->
        GradientBackground(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .padding(top = 120.dp)
            ) {
            ScreenHeader(
                title = "Start drawing!",
                subtitle = "Choose a difficulty setting"
            )
                Spacer(modifier = Modifier.height(56.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    ) {
                    // Iterate through each difficulty option with its index
                    uiState.options.forEachIndexed { index, difficulty ->
                        DifficultyOptions(
                            difficulty = difficulty,
                            index = index,
                            onClick = {
                                viewModel.onEvent(
                                    DifficultySelectionUiEvent.SelectDifficulty(difficulty)
                                )
                            }
                        )
                    }

        }

    }
    }}
}




