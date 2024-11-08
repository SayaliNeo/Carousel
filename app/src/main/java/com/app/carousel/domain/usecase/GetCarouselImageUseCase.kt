package com.app.carousel.domain.usecase

import com.app.carousel.base.BaseUseCase
import com.app.carousel.data.model.Carousel
import com.app.carousel.domain.respository.CarouselRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarouselImageUseCase @Inject constructor(private val catalogRepository: CarouselRepository) :
    BaseUseCase<Unit, List<Carousel>>() {
    override suspend fun execute(params: Unit): Flow<List<Carousel>> {
        return catalogRepository.getCarouselImage()
    }

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
}