package com.example.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scribbledash.features.drawscreen.presentation.DrawScreen
import com.example.scribbledash.features.homescreen.presentation.HomeScreen
import com.example.scribbledash.features.homescreen.presentation.HomeViewModel

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(
            navController,
            viewModel = HomeViewModel(),
        )
         }
//        composable(Screen.Difficulty.route) { DifficultyScreen(navController) }
        composable(Screen.Draw.route) { DrawScreen(navController) }
    }
}