package com.example.scribbledash.di

import android.content.Context
import android.content.res.AssetManager
import com.example.scribbledash.data.repository.DrawingsRepositoryInterface
import com.example.scribbledash.data.repository.AssetDrawingsRepository
import com.example.scribbledash.features.difficultyscreen.domain.drawing.usecase.Comparator
import com.example.scribbledash.features.difficultyscreen.domain.drawing.usecase.DrawingComparer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Provides
    @Singleton
    fun provideDrawingsRepository(assetManager: AssetManager): DrawingsRepositoryInterface {
        return AssetDrawingsRepository(assetManager)
    }

    /**
     * Since `Comparator` is a Kotlin `object`, we expose it via a @Provides method.
     */
    @Module
    @InstallIn(SingletonComponent::class)
    object DrawingComparerModule {

        @Provides
        @Singleton
        fun provideDrawingComparer(): DrawingComparer = Comparator
    }

}
