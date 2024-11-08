package com.app.carousel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.carousel.data.model.Carousel
import com.app.carousel.data.model.CarouselList
import com.app.carousel.domain.model.CarouselAnalysis
import com.app.carousel.domain.model.HomePageBaseUseCase
import com.app.carousel.domain.model.Resource
import com.app.carousel.domain.usecase.GetCarouselAnalysisUseCase
import com.app.carousel.domain.usecase.GetCarouselListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarouselViewModel @Inject constructor(
    private val homePageBaseUseCase: HomePageBaseUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery get() = _searchQuery.asStateFlow()

    private val _carouselImage = MutableStateFlow<List<Carousel>>(emptyList())
    val carouselImage get() = _carouselImage.asStateFlow()

    private val _carouselList = MutableStateFlow<List<CarouselList>>(emptyList())
    val carouselList get() = _carouselList.asStateFlow()

    private val _carouselAnalysis =
        MutableStateFlow<Resource<CarouselAnalysis>>(Resource.loading())
    val carouselAnalysis get() = _carouselAnalysis.asStateFlow()

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet get() = _showBottomSheet.asStateFlow()

    private val _carouselListLoading = MutableStateFlow(true)
     val carouselListLoading get() = _carouselListLoading.asStateFlow()


    private var currentCarouselForDisplay = -1

    fun onCarouselChanged(index: Int) {
        currentCarouselForDisplay = index
        _searchQuery.value = ""
        getCarouselList()
    }

    fun onSearchValueChange(query: String) {
        _searchQuery.value = query
        onSearchTriggered()
    }

    fun onSearchTriggered() {
        getCarouselList()
    }

    private fun getCarouselImage() = viewModelScope.launch {
        homePageBaseUseCase.getCarouselImageUseCase(Unit).collect {
            _carouselImage.emit(it)
        }
    }

    private fun getCarouselList() = viewModelScope.launch {
        if (currentCarouselForDisplay < 0 || carouselImage.value.isEmpty()) {
            return@launch
        }
        val carouselType = carouselImage.value[currentCarouselForDisplay].type
        val param = GetCarouselListUseCase.Params(carouselType, searchQuery.value)
        homePageBaseUseCase.getCarouselListUseCase(param).onStart {
            _carouselListLoading.emit(true)
        }.collect {
            _carouselListLoading.emit(false)
            _carouselList.emit(it)
        }
    }

    fun showBottomSheet() = viewModelScope.launch {
        _showBottomSheet.emit(true)
        startCarouselAnalysis()
    }

    fun hideBottomSheet() = viewModelScope.launch {
        _showBottomSheet.emit(false)
    }

    private fun startCarouselAnalysis() = viewModelScope.launch {
        _carouselAnalysis.emit(Resource.loading())
        val params = GetCarouselAnalysisUseCase.Params(carouselList.value, carouselType = carouselImage.value[currentCarouselForDisplay].type)
        homePageBaseUseCase.getCarouselAnalysisUseCase(params).catch {
            _carouselAnalysis.emit(Resource.error(it))
        }.collect {
            _carouselAnalysis.emit(Resource.success(it))
        }
    }

    init {
        getCarouselImage()
    }

}