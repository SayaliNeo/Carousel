package com.app.carousel.domain.model

data class CarouselAnalysis(
    val itemCount: Int,
    val characterOccurrences: Map<Char, Int>,
    val carouselType: String
)