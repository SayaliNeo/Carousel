package com.app.carousel.domain.usecase

import com.app.carousel.base.BaseUseCase
import com.app.carousel.data.model.CarouselList
import com.app.carousel.data.model.CarouselType
import com.app.carousel.domain.model.CarouselAnalysis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class GetCarouselAnalysisUseCase :
    BaseUseCase<GetCarouselAnalysisUseCase.Params, CarouselAnalysis>() {
    override suspend fun execute(params: Params) = flow {
        requireNotNull(params.carouselList) { "Item list cannot be empty" }
        require(params.carouselList.isNotEmpty()) { "Item list cannot be empty" }

        val occurrence = hashMapOf<Char, Int>()
        for (item in params.carouselList) {
            for (data in item.title) {
                if (data==' ') continue
                occurrence[data] = (occurrence[data] ?: 0) + 1
            }
        }
        val sortedMap =
            occurrence.toList().sortedByDescending { (_, value) -> value }
                .take(params.analysisOutputCount).toMap()
        emit(
            CarouselAnalysis(
                itemCount = params.carouselList.size,
                characterOccurrences = sortedMap,
                carouselType = params.catalogType.toString().lowercase().replaceFirstChar{ it.uppercaseChar() }
            )
        )
    }

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    data class Params(
        val carouselList: List<CarouselList>?,
        val analysisOutputCount: Int = 3,
        val catalogType: CarouselType
    )
}