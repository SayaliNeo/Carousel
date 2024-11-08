package com.app.carousel.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.carousel.R
import com.app.carousel.presentation.components.CarouselWithIndicator
import com.app.carousel.presentation.home.components.CarouselImageItem
import com.app.carousel.presentation.home.components.CarouselListItem
import com.app.carousel.presentation.home.components.CarouselListLoading
import com.app.carousel.presentation.home.components.NoItemFound
import com.app.carousel.presentation.home.components.SearchBox
import com.app.carousel.presentation.theme.primaryColor


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CarouselPage(viewModel: CarouselViewModel = hiltViewModel()) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val catalogList by viewModel.carouselImage.collectAsState()
    val catalogRange by viewModel.carouselList.collectAsState()
    val catalogAnalysis by viewModel.carouselAnalysis.collectAsState()
    val showBottomSheet by viewModel.showBottomSheet.collectAsState()
    val catalogRangeLoading by viewModel.carouselListLoading.collectAsState()

    val pagerState = rememberPagerState { catalogList.size }
    val bottomSheetState = rememberModalBottomSheetState()

    val focusManager = LocalFocusManager.current


    LaunchedEffect(key1 = pagerState.currentPage, key2 = pagerState.pageCount) {
        focusManager.clearFocus()
        viewModel.onCarouselChanged(pagerState.currentPage)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::showBottomSheet,
                containerColor = primaryColor,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.txt_analysis)
                )
            }
        }) { inset ->
        LazyColumn(
            modifier = Modifier
                .padding(inset)
                .padding(top = dimensionResource(id = R.dimen.dp_10))
                .fillMaxSize()
        ) {
            item {
                CarouselWithIndicator(pagerState = pagerState) { index ->
                    CarouselImageItem(
                        resource = catalogList[index].imageRes,
                        des = catalogList[index].type.toString()
                    )
                }
            }
            stickyHeader {
                SearchBox(modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(
                        start = dimensionResource(id = R.dimen.dp_30),
                        end = dimensionResource(id = R.dimen.dp_30),
                        top = dimensionResource(id = R.dimen.dp_10),
                        bottom = dimensionResource(id = R.dimen.dp_10)
                    ),
                    value = searchQuery,
                    onValueChange = viewModel::onSearchValueChange,
                    onSearch = {
                        focusManager.clearFocus()
                        viewModel.onSearchTriggered()
                    })
            }
            itemsIndexed(catalogRange) { _, item ->
                CarouselListItem(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.dp_20),
                            end = dimensionResource(id = R.dimen.dp_20),
                            top = dimensionResource(id = R.dimen.dp_5)
                        )
                        .fillMaxWidth(),
                    data = item
                )
            }
            item {
                Spacer(modifier = Modifier.height( dimensionResource(id = R.dimen.dp_20)))
            }
            item {
                if (catalogRange.isEmpty() && !catalogRangeLoading) {
                    NoItemFound(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = dimensionResource(
                                    id = R.dimen.dp_30
                                ),
                            )
                    )
                }
            }
            item {
                if (catalogRangeLoading) {
                    CarouselListLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = dimensionResource(
                                    id = R.dimen.dp_30
                                ),
                            )
                    )
                }
            }
        }
    }
    if (showBottomSheet) {
        CarouselAnalysisBottomSheet(
            data = catalogAnalysis,
            bottomSheetState = bottomSheetState,
            onDismiss = {
                viewModel.hideBottomSheet()
            },
        )
    }
}

