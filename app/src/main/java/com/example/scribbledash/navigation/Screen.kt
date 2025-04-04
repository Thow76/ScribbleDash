package com.example.scribbledash.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Difficulty : Screen("difficulty")
    object Draw : Screen("draw")
}
