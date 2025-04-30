package com.example.scribbledash.features.homescreen.presentation

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.scribbledash.R
import com.example.scribbledash.core.components.CustomTopAppBar
import com.example.scribbledash.core.components.GradientBackground
import com.example.scribbledash.core.components.ScreenHeader
import com.example.scribbledash.features.homescreen.components.BottomNavigationBar
import com.example.scribbledash.features.homescreen.components.GameModeCard
import com.example.scribbledash.features.homescreen.viewmodel.HomeViewModel
import com.example.scribbledash.features.theme.AppTypography
import com.example.scribbledash.features.theme.Gradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    GradientBackground(
        modifier = Modifier
            .fillMaxSize()
    ){
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CustomTopAppBar(
                    onClose = { navController.popBackStack() },
                    title = {
                        Text(stringResource(R.string.scribble_dash_title),
                        style = AppTypography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        )}
                )
            },
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
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
                ScreenHeader(
                    title = stringResource(R.string.home_screen_title),
                    subtitle = stringResource(R.string.select_game_mode)
                )
                Spacer(modifier = Modifier.height(24.dp)
                )
                GameModeCard(
                    modeName = "One Round Wonder",
                    navController = navController
                )
                            }
                        }
                    }
                }
