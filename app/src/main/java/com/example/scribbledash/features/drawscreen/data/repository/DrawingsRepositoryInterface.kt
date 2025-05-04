package com.example.scribbledash.data.repository

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset

interface DrawingsRepositoryInterface {
    fun getRandomDrawing(): String
    fun loadPaths(name: String): List<List<Offset>>
    fun loadDrawingAsBitmap(path: String): Bitmap
}