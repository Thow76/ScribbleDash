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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.scribbledash.features.drawscreen.presentation.components.createSmoothPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

//@Composable
//fun DrawScreenPreview(targetPaths: List<List<Offset>>, countdown: Int) {
//    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        Canvas(Modifier.fillMaxWidth().aspectRatio(1f)) {
//            targetPaths.forEach { pts ->
//                drawPath(createSmoothPath(pts), color = Color.LightGray, style = Stroke(4f))
//            }
//        }
//        Text(
//            text = countdown.toString(),
//            style = MaterialTheme.typography.displayMedium,
//            modifier = Modifier.align(Alignment.Center)
//        )
//    }
//}

/**
 * Displays the given SVG (from assets/drawings/) in a square Canvas,
 * overlaying a countdown number at the center.
 *
 * @param svgAssetName filename under assets/drawings/, e.g. "apple.svg"
 * @param countdown    current preview countdown to render
 */
@Composable
fun DrawScreenPreview(
    svgAssetName: String,
    countdown: Int
) {
    val context = LocalContext.current

    // Build and remember an ImageLoader with SVG support
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Square SVG container – now also centered internally
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("file:///android_asset/drawings/$svgAssetName")
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            // Countdown overlay – declared after AsyncImage so it draws on top
            Text(
                text = countdown.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onSurface,

            )
        }
    }
}
