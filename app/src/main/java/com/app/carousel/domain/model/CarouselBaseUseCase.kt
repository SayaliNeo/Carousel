package com.app.carousel.domain.model

import com.app.carousel.domain.usecase.GetCarouselAnalysisUseCase
import com.app.carousel.domain.usecase.GetCarouselImageUseCase
import com.app.carousel.domain.usecase.GetCarouselListUseCase

data class CarouselBaseUseCase(
    val getCarouselImageUseCase: GetCarouselImageUseCase,
    val getCarouselListUseCase: GetCarouselListUseCase,
    val getCarouselAnalysisUseCase: GetCarouselAnalysisUseCase
)