package com.example.scribbledash.features.drawscreen.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.scribbledash.features.drawscreen.presentation.model.StrokeData

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    paths: List<StrokeData>,
    currentPath: StrokeData?,
    onDrawStart: (Offset) -> Unit,
    onDrawMove: (Offset) -> Unit,
    onDrawEnd: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp), // Add spacing around the canvas
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(8.dp), // Space between outer and inner border
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(24.dp)) // Rounded corners
                    .background(Color.White)         // Canvas background color
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceContainerLowest,
                        RoundedCornerShape(24.dp)
                    ) // Optional visual edge
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = onDrawStart,
                            onDrag = { change, _ -> onDrawMove(change.position) },
                            onDragEnd = onDrawEnd,
                            onDragCancel = onDrawEnd
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val thirdWidth = width / 3
                    val thirdHeight = height / 3

                    // Draw grid lines (2 vertical + 2 horizontal to make 9 squares)
                    for (i in 1..2) {
                        drawLine(
                            color = Color(0xFFE1D5CA), // Surface Lowest from spec
                            start = Offset(x = i * thirdWidth, y = 0f),
                            end = Offset(x = i * thirdWidth, y = height),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFFE1D5CA),
                            start = Offset(x = 0f, y = i * thirdHeight),
                            end = Offset(x = width, y = i * thirdHeight),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Draw completed paths
                    paths.forEach { strokeData ->
                        drawPath(
                            path = createSmoothPath(strokeData.points),
                            color = strokeData.color,
                            style = strokeData.toStroke()
                        )
                    }

                    // Draw the current path being drawn
                    currentPath?.let { strokeData ->
                        drawPath(
                            path = createSmoothPath(strokeData.points),
                            color = strokeData.color,
                            style = strokeData.toStroke()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Creates a smooth path from points using Bezier curves
 */
fun createSmoothPath(points: List<Offset>): Path {
    val path = Path()
    if (points.isEmpty()) return path

    if (points.size == 1) {
        // Single point - draw a tiny line to make it visible
        path.moveTo(points[0].x, points[0].y)
        path.lineTo(points[0].x, points[0].y + 0.1f) // Add a tiny segment to render point
        return path
    }

    // Start path at the first point
    path.moveTo(points[0].x, points[0].y)

    if (points.size == 2) {
        // Two points - simple line
        path.lineTo(points[1].x, points[1].y)
    } else {
        // Three or more points - use cubic Bezier curves for smoothing
        for (i in 1 until points.size) {
            if (i < points.size - 1) {
                // Calculate control points
                val xc = (points[i].x + points[i + 1].x) / 2
                val yc = (points[i].y + points[i + 1].y) / 2

                // Add smooth curve
                path.quadraticBezierTo(points[i].x, points[i].y, xc, yc)
            } else {
                // Last point
                path.lineTo(points[i].x, points[i].y)
            }
        }
    }

    return path
}