package com.example.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.difficultyscreen.presentation.DifficultySelectionScreen
import com.example.scribbledash.features.drawscreen.presentation.DrawScreen
import com.example.scribbledash.features.homescreen.presentation.HomeScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(
            navController,
        )
         }
       composable(Screen.Difficulty.route) {
           DifficultySelectionScreen(
               navController) }
        composable(
            route = "${Screen.Draw.route}/{difficulty}",
            arguments = listOf(
                navArgument("difficulty") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStack ->
            val difficulty = backStack.arguments?.getString("difficulty")
                ?: Difficulty.Beginner.name
            DrawScreen(
                navController = navController,
                difficulty = Difficulty.valueOf(difficulty)
            )
        }
}}