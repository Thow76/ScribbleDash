package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.scribbledash.R
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas
import com.example.scribbledash.features.drawscreen.presentation.components.createSmoothPath
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import com.example.scribbledash.features.utils.BoundsCalculator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreenResult(
    score: Int,
    onRetry: () -> Unit,
    onClose: () -> Unit,
    userPaths: List<StrokeData>,
    svgAssetName: String
) {
    // SET UP COIL FOR STATIC SVG EXAMPLE
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Close button
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Score
            Text(
                text = "$score%",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            // Example vs. Drawing
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // STATIC EXAMPLE
                ResultCard(label = "Example", rotation = -8f) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/drawings/$svgAssetName")
                            .build(),
                        imageLoader = imageLoader,
                        contentDescription = "Example drawing",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // USER DRAWING: let DrawingCanvas handle scaling automatically
                ResultCard(label = "Drawing", rotation = 8f) {
                    // Calculate a slightly smaller bounding box to ensure strokes aren't cut off
                    val adjustedBoundingBox = remember(userPaths) {
                        if (userPaths.isEmpty()) null
                        else {
                            val allPoints = userPaths.flatMap { it.points }
                            BoundsCalculator.calculateBounds(allPoints, 0.1f)
                        }
                    }

                    DrawingCanvas(
                        modifier = Modifier.fillMaxSize(),
                        paths = userPaths,
                        boundingBox = adjustedBoundingBox,  // Use our padded bounding box
                        currentPath = null,
                        contentPadding = PaddingValues(0.dp),
                        onDrawStart = {},
                        onDrawMove = {},
                        onDrawEnd = {}
                    )
                }
            }

            // Title & subtitle
            val (title, subtitle) = when (score) {
                100 -> "Woohoo!"  to "You've officially raised the bar! I'm going to need a ladder to reach it!"
                0   -> "Oops"     to "If this was a treasure map, I'd be lost for sure!"
                else-> "Nice try!" to "Keep practicing and see if you can get all the way up!"
            }
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // Retry button
            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF238CFF),
                    contentColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            ) {
                Text(
                    text = "Try Again",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
private fun ResultCard(
    label: String,
    rotation: Float = 0f,
    content: @Composable BoxWithConstraintsScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(140.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .size(140.dp)
                .graphicsLayer { rotationZ = rotation }
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            BoxWithConstraints (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                content = content
            )
        }
    }
}

