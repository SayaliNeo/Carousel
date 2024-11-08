package com.app.carousel.domain.respository

import com.app.carousel.data.model.Carousel
import com.app.carousel.data.model.CarouselList
import com.app.carousel.data.model.CarouselType
import kotlinx.coroutines.flow.Flow

interface CarouselRepository {
    suspend fun getCarouselImage(): Flow<List<Carousel>>
    suspend fun getCarouselList(carouselType: CarouselType): Flow<List<CarouselList>>
}