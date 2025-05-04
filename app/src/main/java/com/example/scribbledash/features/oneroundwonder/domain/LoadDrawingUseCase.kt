package com.example.scribbledash.features.oneroundwonder.domain

import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import javax.inject.Inject

class LoadDrawingsUseCase @Inject constructor(
    private val drawingsRepository: DrawingsRepositoryInterface
) {
    fun random(): String {
        return drawingsRepository.getRandomDrawing()
    }
}