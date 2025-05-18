package com.example.scribbledash.features.drawscreen.presentation.components

import androidx.compose.ui.geometry.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import android.graphics.RectF
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.scribbledash.features.drawscreen.components.scaleImage
import kotlin.math.min

@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    paths: List<StrokeData>,
    currentPath: StrokeData?,
    /** Optional bounding box of strokes in original coords. */
    boundingBox: Rect? = null,
    /** Whether to auto-fit strokes into the canvas bounds. */
    autoFit: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
    onDrawStart: (Offset) -> Unit,
    onDrawMove: (Offset) -> Unit,
    onDrawEnd: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(28.dp))
                .background(backgroundColor)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceContainerLowest,
                        RoundedCornerShape(24.dp)
                    )
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
                    val (scale, dx, dy) = scaleImage(
                        paths,
                        currentPath,
                        boundingBox,
                        autoFit,
                        size.width,
                        size.height
                    )

                    // Draw 3×3 grid
                    val thirdW = size.width / 3f
                    val thirdH = size.height / 3f
                    repeat(2) { i ->
                        drawLine(
                            color = Color(0xFFE1D5CA),
                            start = Offset(thirdW * (i + 1), 0f),
                            end = Offset(thirdW * (i + 1), size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFFE1D5CA),
                            start = Offset(0f, thirdH * (i + 1)),
                            end = Offset(size.width, thirdH * (i + 1)),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Draw strokes
                    val toDraw = paths + listOfNotNull(currentPath)
                    toDraw.forEach { strokeData ->
                        val transformed = strokeData.points.map { pt ->
                            Offset(
                                x = pt.x * scale + dx,
                                y = pt.y * scale + dy
                            )
                        }
                        drawPath(
                            path = createSmoothPath(transformed),
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
        path.moveTo(points[0].x, points[0].y)
        path.lineTo(points[0].x, points[0].y + 0.1f)
        return path
    }
    path.moveTo(points[0].x, points[0].y)
    if (points.size == 2) {
        path.lineTo(points[1].x, points[1].y)
    } else {
        for (i in 1 until points.size) {
            if (i < points.size - 1) {
                val xc = (points[i].x + points[i + 1].x) / 2
                val yc = (points[i].y + points[i + 1].y) / 2
                path.quadraticBezierTo(points[i].x, points[i].y, xc, yc)
            } else {
                path.lineTo(points[i].x, points[i].y)
            }
        }
    }
    return path
}






