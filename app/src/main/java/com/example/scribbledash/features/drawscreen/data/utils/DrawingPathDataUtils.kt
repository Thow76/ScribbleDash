package com.example.scribbledash.data.utils


import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.PathParser

object DrawingPathDataUtils {
    fun toPathPoints(nodes: Array<PathParser.PathDataNode>): List<Offset> {
        // Basic implementation to convert path nodes to points
        val points = mutableListOf<Offset>()
        // Path conversion logic here
        return points
    }
}