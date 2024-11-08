package com.app.carousel.data.repository

import com.app.carousel.data.store.CarouselStore
import com.app.carousel.data.model.CarouselType
import com.app.carousel.domain.respository.CarouselRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarouselRepositoryImpl @Inject constructor(private val dataStore: CarouselStore) :
    CarouselRepository {

    override suspend fun getCarouselImage() = flow {
        emit(dataStore.carouselList)
    }

    override suspend fun getCarouselList(carouselType: CarouselType) = flow {
        val response = when (carouselType) {
            CarouselType.SPORTS -> dataStore.sportsSubItems
            CarouselType.PLANT -> dataStore.plantSubItems
            CarouselType.FOOD -> dataStore.indianFoodSubItems
            CarouselType.SHOE -> dataStore.shoeSubItems
            CarouselType.COUNTRY -> dataStore.countrySubItems
        }
        emit(response)
    }

}