package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import com.example.scribbledash.features.difficultyscreen.domain.Difficulty
import com.example.scribbledash.features.drawscreen.presentation.components.DrawingCanvas
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingActionUiEvent
import com.example.scribbledash.features.drawscreen.presentation.state.DrawingState
import com.example.scribbledash.features.drawscreen.viewmodel.DrawingViewModel

@Composable
fun DrawScreenPreview(
    svgAssetName: String,
    targetPaths: List<List<Offset>>,
    onDrawStart: (Offset) -> Unit,
    onDrawMove:  (Offset) -> Unit,
    onDrawEnd:   () -> Unit
) {
    val context     = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Ready, set...", style = MaterialTheme.typography.displayMedium)

        // exactly the same “outer” inset as your real canvas
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // 28 dp clip + background + 8 dp padding
            Box(
                Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(MaterialTheme.colorScheme.surface),
                // .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                // SVG in the background
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("file:///android_asset/drawings/$svgAssetName")
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize()
                )

                // ← here we zero-out DrawingCanvas’s own 24/16 padding
                DrawingCanvas(
                    modifier = Modifier.matchParentSize(),
                    backgroundColor = Color.Transparent,
                    paths = emptyList(),
                    autoFit = false,
                    currentPath = null,
                    contentPadding = PaddingValues(0.dp),  // **override**!
                    onDrawStart = onDrawStart,
                    onDrawMove = onDrawMove,
                    onDrawEnd = onDrawEnd
                )


            }

        }
        Text(
            "Example",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}



