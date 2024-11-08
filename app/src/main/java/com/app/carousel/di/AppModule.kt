package com.app.carousel.di

import com.app.carousel.data.store.CarouselStore
import com.app.carousel.domain.model.CarouselBaseUseCase
import com.app.carousel.domain.usecase.GetCarouselAnalysisUseCase
import com.app.carousel.domain.usecase.GetCarouselImageUseCase
import com.app.carousel.domain.usecase.GetCarouselListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCarouselStore(): CarouselStore {
        return CarouselStore()
    }

    @Provides
    fun provideHomePageBaseUseCase(
        getCatalogListUseCase: GetCarouselImageUseCase,
        getCatalogRangeUseCase: GetCarouselListUseCase,
        getCatalogAnalysisUseCase: GetCarouselAnalysisUseCase
    ): CarouselBaseUseCase {
        return CarouselBaseUseCase(
            getCarouselImageUseCase = getCatalogListUseCase,
            getCarouselListUseCase = getCatalogRangeUseCase,
            getCarouselAnalysisUseCase = getCatalogAnalysisUseCase
        )
    }

    @Provides
    fun provideCatalogAnalysisUseCase(): GetCarouselAnalysisUseCase {
        return GetCarouselAnalysisUseCase()
    }
}