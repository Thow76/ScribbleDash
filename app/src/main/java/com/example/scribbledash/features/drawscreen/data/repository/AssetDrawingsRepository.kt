package com.example.scribbledash.data.repository

import android.content.res.AssetManager
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

    // 1) List only .svg files in assets/drawings/
    private val drawingFiles: List<String> by lazy {
        assetManager.list("drawings")
            ?.filter { it.endsWith(".svg", ignoreCase = true) }
            .orEmpty()
    }

    // 2) Return a random SVG filename
    override fun getRandomDrawing(): String =
        drawingFiles.random()


    override fun loadPaths(name: String): List<List<Offset>> {
        val raw = assetManager.open("drawings/$name").bufferedReader().readText()
        val parser = Xml.newPullParser().apply { setInput(StringReader(raw)) }
        val result = mutableListOf<List<Offset>>()

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                // SVG uses "d" instead of "android:pathData"
                parser.getAttributeValue(null, "d")?.let { d ->
                    val nodes = PathParser.createNodesFromPathData(d)
                    result += DrawingPathDataUtils.toPathPoints(nodes)
                }
            }
            parser.next()
        }
        return result
//    }
}}