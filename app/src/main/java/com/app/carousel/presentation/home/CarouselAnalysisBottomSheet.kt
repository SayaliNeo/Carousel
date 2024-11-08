package com.app.carousel.presentation.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.app.carousel.R
import com.app.carousel.domain.model.CarouselAnalysis
import com.app.carousel.domain.model.Resource
import com.app.carousel.domain.model.ResourceState
import com.app.carousel.presentation.components.AppBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselAnalysisBottomSheet(
    modifier: Modifier = Modifier,
    data: Resource<CarouselAnalysis>,
    onDismiss: () -> Unit,
    bottomSheetState: SheetState
) {
    AppBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        when (data.status) {
            ResourceState.LOADING -> Text(stringResource(R.string.txt_loading))
            ResourceState.SUCCESS -> {
                Text(text = stringResource(R.string.txt_item_count, data.data?.carouselType.toString(),data.data?.itemCount ?: 0))
                Text(text = stringResource(R.string.top_3_characters))
                data.data?.characterOccurrences?.map {data->
                    Text(text = stringResource(R.string.list_character_count,data.key,data.value))
                }
            }
            ResourceState.ERROR -> Text(
                text = data.error ?: stringResource(R.string.txt_error_occurred)
            )
        }
    }
}