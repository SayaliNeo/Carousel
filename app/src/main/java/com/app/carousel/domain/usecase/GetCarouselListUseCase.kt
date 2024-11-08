package com.app.carousel.domain.usecase

import com.app.carousel.base.BaseUseCase
import com.app.carousel.data.model.CarouselList
import com.app.carousel.data.model.CarouselType
import com.app.carousel.domain.respository.CarouselRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCarouselListUseCase @Inject constructor(private val catalogRepository: CarouselRepository) :
    BaseUseCase<GetCarouselListUseCase.Params, List<CarouselList>>() {

    override suspend fun execute(params: Params): Flow<List<CarouselList>> {
        return catalogRepository.getCarouselList(params.carouselType)
            .map { responseList ->
                responseList.filter { item ->
                    item.title.contains(params.searchQuery, ignoreCase = true)
                }
            }

    }

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    data class Params(val carouselType: CarouselType, val searchQuery: String)
}