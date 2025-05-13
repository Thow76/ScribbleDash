package com.example.scribbledash.data.repository

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset

interface DrawingsRepositoryInterface {
    /** Returns the filename (e.g. "apple.svg") of a random drawing in assets/drawings/ */
    fun getRandomDrawing(): String

    /** Parses the given SVG asset into a list of stroke-paths (each a List<Offset>) */
    fun loadPaths(name: String): List<List<Offset>>
    //fun loadDrawingAsBitmap(path: String): Bitmap
}