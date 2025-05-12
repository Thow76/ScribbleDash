package com.example.scribbledash.features.drawscreen.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.scribbledash.features.drawscreen.presentation.components.createSmoothPath
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

/**
 * Displays the given SVG (from assets/drawings/) in a square Canvas,
 * overlaying a countdown number at the center.
 *
 * @param svgAssetName filename under assets/drawings/, e.g. "apple.svg"
 * @param countdown    current preview countdown to render
 */

//@Composable
//fun DrawScreenPreview(
//    svgAssetName: String,
//    countdown: Int,
//    targetPaths: List<List<Offset>>,
//    onDrawStart: (Offset)->Unit,
//    onDrawMove:  (Offset)->Unit,
//    onDrawEnd:   ()->Unit
//) {
//
//    val context = LocalContext.current
//
//    // Build and remember an ImageLoader with SVG support
//    val imageLoader = remember {
//        ImageLoader.Builder(context)
//            .components { add(SvgDecoder.Factory()) }
//            .build()
//    }
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Time to Draw!", style = MaterialTheme.typography.displaySmall)
//        Box(
//            Modifier
//                .fillMaxSize()
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(28.dp))
//                .background(MaterialTheme.colorScheme.onPrimary)
//                .padding(8.dp),
////            contentAlignment = Alignment.Center
//
//        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(context)
//                    .data("file:///android_asset/drawings/$svgAssetName")
//                    .build(),
//                imageLoader = imageLoader,
//                contentDescription = null,
//                modifier = Modifier.padding(32.dp)
//                    .fillMaxWidth()
//            )
//            DrawingCanvas(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f),
//                backgroundColor = Color.Transparent,
//                paths = emptyList(),       // no user strokes yet
//                currentPath = null,
//                onDrawStart = onDrawStart,
//                onDrawMove = onDrawMove,
//                onDrawEnd = onDrawEnd
//            )
//            Text(
//                text = countdown.toString(),
//                style = MaterialTheme.typography.displayMedium,
//                color = MaterialTheme.colorScheme.onSurface,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//}

//@Composable
//fun DrawScreenPreview(
//    svgAssetName: String,
//    countdown: Int,
//    targetPaths: List<List<Offset>>,
//    onDrawStart: (Offset)->Unit,
//    onDrawMove:  (Offset)->Unit,
//    onDrawEnd:   ()->Unit
//) {
//    val context = LocalContext.current
//
//    // 1) keep your ImageLoader
//    val imageLoader = remember {
//        ImageLoader.Builder(context)
//            .components { add(SvgDecoder.Factory()) }
//            .build()
//    }
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Time to Draw!", style = MaterialTheme.typography.displaySmall)
//
//        // 2) outer “padding” wrapper exactly like DrawingCanvas
//        Box(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 24.dp, vertical = 16.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            // 3) first rounded shape (28dp) + background
//            Box(
//                Modifier
//                    .aspectRatio(1f)
//                    .clip(RoundedCornerShape(28.dp))
//                    .background(MaterialTheme.colorScheme.surface)
//                    .padding(8.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                // 4a) your SVG as a full-size background
//                AsyncImage(
//                    model = ImageRequest.Builder(context)
//                        .data("file:///android_asset/drawings/$svgAssetName")
//                        .build(),
//                    imageLoader = imageLoader,
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize()
//                )
//
//                // 4b) the “inner” rounded border (24dp) + grid/strokes canvas
//                DrawingCanvas(
//                    modifier = Modifier.matchParentSize(),
//                    backgroundColor = Color.Transparent,
//                    // if you actually want to show the targetPaths here,
//                    // map them into StrokeData; otherwise you can leave this empty.
//                    paths = emptyList(),
//                    currentPath = null,
//                    onDrawStart = onDrawStart,
//                    onDrawMove = onDrawMove,
//                    onDrawEnd = onDrawEnd
//                )
//
//                // 4c) countdown over the very top
//                Text(
//                    text = countdown.toString(),
//                    style = MaterialTheme.typography.displayMedium,
//                    color = MaterialTheme.colorScheme.onSurface,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//    }
//}

//@Composable
//fun DrawScreenPreview(
//    svgAssetName: String,
//    countdown: Int,
//    targetPaths: List<List<Offset>>,
//    onDrawStart: (Offset) -> Unit,
//    onDrawMove:  (Offset) -> Unit,
//    onDrawEnd:   () -> Unit
//) {
//    val context = LocalContext.current
//    val imageLoader = remember {
//        ImageLoader.Builder(context)
//            .components { add(SvgDecoder.Factory()) }
//            .build()
//    }
//
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text("Time to Draw!", style = MaterialTheme.typography.displaySmall)
//
//        // Only the 28dp→8dp container here:
//        Box(
//            Modifier
//                .fillMaxWidth()
//                .aspectRatio(1f)
//                .clip(RoundedCornerShape(28.dp))
//                .background(MaterialTheme.colorScheme.onPrimary)
//                .padding(8.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            // SVG behind
//            AsyncImage(
//                model = ImageRequest.Builder(context)
//                    .data("file:///android_asset/drawings/$svgAssetName")
//                    .build(),
//                imageLoader = imageLoader,
//                contentDescription = null,
//                modifier = Modifier.matchParentSize()
//            )
//
//            // This will now apply the *only* 24/16 padding exactly once
//            DrawingCanvas(
//                modifier = Modifier.fillMaxSize(),
//                backgroundColor = Color.Transparent,
//                paths = emptyList(),
//                currentPath = null,
//                onDrawStart = onDrawStart,
//                onDrawMove  = onDrawMove,
//                onDrawEnd   = onDrawEnd
//            )
//
//            // countdown
//            Text(
//                countdown.toString(),
//                style = MaterialTheme.typography.displayMedium,
//                color = MaterialTheme.colorScheme.onSurface,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//}

@Composable
fun DrawScreenPreview(
    svgAssetName: String,
    countdown: Int,
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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Time to Draw!", style = MaterialTheme.typography.displaySmall)

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
                    imageLoader       = imageLoader,
                    contentDescription= null,
                    modifier          = Modifier.matchParentSize()
                )

                // ← here we zero-out DrawingCanvas’s own 24/16 padding
                DrawingCanvas(
                    modifier       = Modifier.matchParentSize(),
                    backgroundColor= Color.Transparent,
                    paths          = emptyList(),
                    currentPath    = null,
                    contentPadding = PaddingValues(0.dp),  // **override**!
                    onDrawStart    = onDrawStart,
                    onDrawMove     = onDrawMove,
                    onDrawEnd      = onDrawEnd
                )

                // countdown on top
                Text(
                    text     = countdown.toString(),
                    style    = MaterialTheme.typography.displayMedium,
                    color    = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}



