package com.example.scribbledash.features.oneroundwonder.presentation

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scribbledash.features.oneroundwonder.state.OneRoundState
import kotlinx.coroutines.delay


@Composable
fun PreviewScreen(
    preview: OneRoundState.Preview,
    onTick: () -> Unit
) {
    // Fire a tick every second
    LaunchedEffect(preview.secondsLeft) {
        delay(1_000L)
        onTick()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ready, set…",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        // Display the SVG reference drawing
        val context = LocalContext.current
        val inputStream = context.assets.open("drawings/${preview.examplePath}")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val imageBitmap = bitmap.asImageBitmap()

        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "${preview.secondsLeft} second${if (preview.secondsLeft > 1) "s" else ""} left",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
