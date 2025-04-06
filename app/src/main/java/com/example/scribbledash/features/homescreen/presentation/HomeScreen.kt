package com.example.scribbledash.features.homescreen.presentation

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scribbledash.R
import com.example.scribbledash.features.theme.AppTypography
import com.example.scribbledash.features.theme.Gradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    //onNavigateToDrawScreen: () -> Unit, // Callback for navigation
    //onNavigateToOtherScreen: () -> Unit // Future or second tab callback
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = com.example.scribbledash.features.theme.Gradient.mainGradient) // Background gradient
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.scribble_dash_title),
                            style = AppTypography.headlineLarge,
                            color = MaterialTheme.colorScheme.onBackground,

                        ) // App title at the top left
                    }
                )
            },
                    bottomBar = {
                        BottomNavigationBar(
                            onHomeClicked = { /* Already on Home */ },
                            onOtherClicked = {
                                //onNavigateToOtherScreen()
                                }
                        )
                    }
        ) { innerPadding ->
            // Main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                            Text(
                                text = stringResource(R.string.home_screen_title),
                                style = MaterialTheme.typography.displayMedium
                            )
                Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.select_game_mode),
                                style = MaterialTheme.typography.bodyMedium
                            )

                Spacer(modifier = Modifier.height(24.dp))

                // Game Mode UI
                            GameModeCard(
                                modeName = "One Round Wonder",
                                onClick = {
                                    // Inform ViewModel that the card was clicked
                                   // viewModel.handleEvent(HomeUiEvent.OnGameModeClicked)
                                    // Then navigate
                                    //onNavigateToDrawScreen()
                                }
                            )
            }
        }
    }
}