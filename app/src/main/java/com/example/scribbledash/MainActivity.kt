package com.example.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.scribbledash.navigation.NavigationRoot
import com.example.scribbledash.features.theme.ScribbleDashTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScribbleDashTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScribbleDashApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ScribbleDashApp(modifier: Modifier) {
    NavigationRoot()
}
