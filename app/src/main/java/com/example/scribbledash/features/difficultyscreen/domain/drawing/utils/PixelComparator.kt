package com.example.scribbledash.features.utils

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Milestone 2 Step 4 + 5:
 * Compare two equally-sized Bitmaps pixel-by-pixel to compute the coverage ratio:
 *   matchedUserPixels / visibleUserPixels
 */
object PixelComparator {

    /**
     * Iterate every pixel in [userBitmap] and [exampleBitmap], counting:
     *  - visibleUserPixels: those where the userBitmap pixel is non-transparent
     *  - matchedUserPixels: those where both userBitmap and exampleBitmap pixels are non-transparent
     *
     * @param userBitmap    Bitmap containing the user’s normalized drawing
     * @param exampleBitmap Bitmap containing the example’s normalized drawing
     * @return coverage ratio in [0f..1f]
     */
    fun pixelCoverage(
        userBitmap: Bitmap,
        exampleBitmap: Bitmap
    ): Float {
        require(userBitmap.width  == exampleBitmap.width &&
                userBitmap.height == exampleBitmap.height) {
            "Bitmaps must be the same dimensions for pixel-by-pixel comparison"
        }

        val width  = userBitmap.width
        val height = userBitmap.height

        var visibleUserPixels = 0
        var matchedUserPixels = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                // Extract alpha channel (0 = fully transparent, 255 = fully opaque)
                val ua = Color.alpha(userBitmap.getPixel(x, y))
                if (ua == 0) {
                    // transparent in user → ignore
                    continue
                }

                visibleUserPixels++

                val ea = Color.alpha(exampleBitmap.getPixel(x, y))
                if (ea > 0) {
                    matchedUserPixels++
                }
            }
        }

        return if (visibleUserPixels == 0) {
            0f
        } else {
            matchedUserPixels.toFloat() / visibleUserPixels.toFloat()
        }
    }
}
