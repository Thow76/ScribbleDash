package com.example.scribbledash.features.oneroundwonder.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.scribbledash.features.oneroundwonder.state.OneRoundState

// ResultScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    resultState: OneRoundState.Result,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val (score, rating, exampleBmp, userBmp) = resultState.result
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Results") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Try Again")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("$score%", style = MaterialTheme.typography.displayLarge)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    bitmap = exampleBmp,
                    contentDescription = "Example",
                    modifier = Modifier
                        .size(150.dp)
                        .graphicsLayer(rotationZ = -5f)
                )
                Image(
                    bitmap = userBmp,
                    contentDescription = "Your Drawing",
                    modifier = Modifier
                        .size(150.dp)
                        .graphicsLayer(rotationZ = 5f)
                )
            }
            Text(rating, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
