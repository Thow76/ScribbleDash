package com.example.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scribbledash.features.difficultyscreen.presentation.DifficultyScreen
import com.example.scribbledash.features.drawscreen.presentation.DrawScreen
import com.example.scribbledash.features.homescreen.presentation.HomeScreen
import com.example.scribbledash.features.oneroundwonder.presentation.OneRoundScreen

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
           DifficultyScreen(
               navController) }
        composable(Screen.Draw.route) {
            DrawScreen(
                navController,
                ) }
        composable(
            route = Screen.OneRoundWonder.route,
            arguments = listOf(navArgument("difficulty") { type = NavType.StringType })
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getString("difficulty")
            OneRoundScreen(navController)
        }
    }
}