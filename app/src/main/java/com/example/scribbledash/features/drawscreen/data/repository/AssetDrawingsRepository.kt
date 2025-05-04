package com.example.scribbledash.data.repository

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Xml
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.PathParser
import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import com.example.scribbledash.data.utils.DrawingPathDataUtils
import org.xmlpull.v1.XmlPullParser
import java.io.StringReader
import javax.inject.Inject

class AssetDrawingsRepository @Inject constructor(
    private val assetManager: AssetManager
) : DrawingsRepositoryInterface {
    private val drawingFiles by lazy {
        assetManager.list("drawings")
            ?.filter { it.endsWith(".xml") }
            .orEmpty()
    }

    override fun getRandomDrawing() = drawingFiles.random()

    override fun loadPaths(name: String): List<List<Offset>> {
        val raw = assetManager.open("drawings/$name").bufferedReader().use { it.readText() }
        val parser = Xml.newPullParser().apply { setInput(StringReader(raw)) }
        val result = mutableListOf<List<Offset>>()

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                parser.getAttributeValue(null, "android:pathData")?.let { d ->
                    val nodes = PathParser.createNodesFromPathData(d)
                    result += DrawingPathDataUtils.toPathPoints(nodes)
                }
            }
            parser.next()
        }

        return result
    }
    override fun loadDrawingAsBitmap(path: String): Bitmap {
        val inputStream = assetManager.open("drawings/$path")
        return BitmapFactory.decodeStream(inputStream)
    }

}