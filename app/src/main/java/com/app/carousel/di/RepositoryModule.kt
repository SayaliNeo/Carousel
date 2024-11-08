package com.app.carousel.di

import com.app.carousel.data.repository.CarouselRepositoryImpl
import com.app.carousel.domain.respository.CarouselRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideCarouselRepository(carouselRepository: CarouselRepositoryImpl): CarouselRepository
}
