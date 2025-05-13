package com.example.scribbledash.features.oneroundwonder.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.scribbledash.features.oneroundwonder.state.OneRoundEvent
import com.example.scribbledash.features.oneroundwonder.state.OneRoundState
import com.example.scribbledash.features.oneroundwonder.viewmodel.OneRoundViewModel

// OneRoundScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneRoundScreen(
    navController: NavHostController,
    oneRoundViewModel: OneRoundViewModel = hiltViewModel()
) {
    val state by oneRoundViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("One Round Wonder") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when (state) {
                is OneRoundState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is OneRoundState.Preview -> {
                    PreviewScreen(
                        preview = state as OneRoundState.Preview,
                        onTick  = {
                             oneRoundViewModel.onEvent(OneRoundEvent.Tick)
                        }
                    )
                }
                is OneRoundState.Drawing -> {
                    OneRoundDrawScreen(
                        onBack   = { navController.popBackStack() },
                        onSubmit = {
                             oneRoundViewModel.onEvent(OneRoundEvent.Submit)
                        }
                    )
                }
                is OneRoundState.Result -> {
                    ResultScreen(
                        resultState = state as OneRoundState.Result,
                        onRetry     = {
                            oneRoundViewModel.onEvent(OneRoundEvent.Retry)
                                      },
                        onBack      = { navController.popBackStack() }

                    )
                }
                is OneRoundState.Error -> {
                    Text(
                        text = (state as OneRoundState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
