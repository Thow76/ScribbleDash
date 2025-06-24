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
import android.util.Log
import androidx.compose.foundation.layout.*
import com.example.scribbledash.features.drawscreen.components.scaleImage
import com.example.scribbledash.features.utils.DrawingUtils
import kotlin.math.min

private const val TAG = "DrawingCanvas"

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
    Log.d(TAG, "DrawingCanvas composable: paths.size=${paths.size}, " +
            "currentPath=${if (currentPath != null) "present" else "null"}, " +
            "boundingBox=${boundingBox?.toString() ?: "null"}, " +
            "autoFit=$autoFit")

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
                            onDragStart = { offset ->
                                Log.d(TAG, "onDrawStart: raw offset=$offset")
                                onDrawStart(offset)
                            },
                            onDrag = { change, _ ->
                                Log.v(TAG, "onDrawMove: raw offset=${change.position}")
                                onDrawMove(change.position)
                            },
                            onDragEnd = {
                                Log.d(TAG, "onDrawEnd")
                                onDrawEnd()
                            },
                            onDragCancel = {
                                Log.d(TAG, "onDrawCancel")
                                onDrawEnd()
                            }
                        )
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    Log.d(TAG, "Canvas drawing: size=${size.width}x${size.height}")

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
                    Log.d(TAG, "Drawing strokes: count=${toDraw.size}, applying scale=$scale, dx=$dx, dy=$dy")

                    toDraw.forEachIndexed { index, strokeData ->
                        // For verbose logging, only log full point details for the first stroke
                        if (index == 0 && strokeData.points.isNotEmpty()) {
                            val firstPoint = strokeData.points.first()
                            val lastPoint = strokeData.points.last()
                            Log.v(TAG, "Stroke $index: points=${strokeData.points.size}, " +
                                    "first=(${firstPoint.x},${firstPoint.y}), " +
                                    "last=(${lastPoint.x},${lastPoint.y})")

                            // Log example of transformation for first and last points
                            val transformedFirst = Offset(firstPoint.x * scale + dx, firstPoint.y * scale + dy)
                            val transformedLast = Offset(lastPoint.x * scale + dx, lastPoint.y * scale + dy)
                            Log.v(TAG, "Transformed: first=$transformedFirst, last=$transformedLast")
                        }

                        val transformed = strokeData.points.map { pt ->
                            Offset(
                                x = pt.x * scale + dx,
                                y = pt.y * scale + dy
                            )
                        }

                        try {
                            val path = createSmoothPath(transformed)
                            drawPath(
                                path = path,
                                color = strokeData.color,
                                style = strokeData.toStroke()
                            )
                            Log.v(TAG, "Drew path $index with ${transformed.size} points, color=${strokeData.color}")
                        } catch (e: Exception) {
                            Log.e(TAG, "Error drawing path $index: ${e.message}", e)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Creates a smooth path from points using Bezier curves
 */
fun createSmoothPath(points: List<Offset>): Path = DrawingUtils.smoothPath(points)
