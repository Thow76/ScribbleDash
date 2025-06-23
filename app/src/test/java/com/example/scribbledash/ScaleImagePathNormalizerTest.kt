package com.example.scribbledash

import android.graphics.Path
import android.graphics.RectF
import com.example.scribbledash.features.drawscreen.components.scaleImage
import com.example.scribbledash.features.utils.PathNormalizer
import androidx.compose.ui.geometry.Rect
import org.junit.Assert.assertEquals
import org.junit.Test

class ScaleImagePathNormalizerTest {
    @Test
    fun scaleImage_centersBoundingBox() {
        val result = scaleImage(
            paths = emptyList(),
            currentPath = null,
            boundingBox = Rect(0f, 0f, 100f, 100f),
            autoFit = true,
            canvasW = 200f,
            canvasH = 300f
        )
        assertEquals(2f, result.first)
        assertEquals(0f, result.second)
        assertEquals(50f, result.third)
    }

    @Test
    fun pathNormalizer_alignsTopLeft() {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(100f, 0f)
            lineTo(100f, 100f)
            close()
        }
        val normalized = PathNormalizer.normalizePaths(
            paths = listOf(path),
            strokeWidth = 0f,
            exampleStrokeWidth = 0f,
            canvasSizePx = 300
        )
        val bounds = RectF().apply { normalized[0].computeBounds(this, true) }
        assertEquals(0f, bounds.left)
        assertEquals(0f, bounds.top)
    }
}
