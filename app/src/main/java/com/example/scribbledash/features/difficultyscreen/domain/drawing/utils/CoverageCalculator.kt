package com.example.scribbledash.features.utils

import kotlin.math.roundToInt

/**
 * Milestone 2 Step 5:
 * Turn the coverage ratio [0f…1f] into a 0–100% integer.
 */
object CoverageCalculator {
    /**
     * @param coverageRatio result of PixelComparator.pixelCoverage(...)
     * @return              rounded percentage in [0..100]
     */
    fun computeCoveragePercent(coverageRatio: Float): Int {
        // multiply by 100 and round to nearest int
        return (coverageRatio * 100f)
            .roundToInt()
            .coerceIn(0, 100)
    }
}
