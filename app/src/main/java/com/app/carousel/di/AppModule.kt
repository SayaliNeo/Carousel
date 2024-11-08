package com.app.carousel.di

import com.app.carousel.data.store.CarouselStore
import com.app.carousel.domain.model.HomePageBaseUseCase
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
        getCarouselImageUseCase: GetCarouselImageUseCase,
        getCarouselListUseCase: GetCarouselListUseCase,
        getCarouselAnalysisUseCase: GetCarouselAnalysisUseCase
    ): HomePageBaseUseCase {
        return HomePageBaseUseCase(
            getCarouselImageUseCase = getCarouselImageUseCase,
            getCarouselListUseCase = getCarouselListUseCase,
            getCarouselAnalysisUseCase = getCarouselAnalysisUseCase
        )
    }

    @Provides
    fun provideCarouselAnalysisUseCase(): GetCarouselAnalysisUseCase {
        return GetCarouselAnalysisUseCase()
    }
}