package com.app.carousel.presentation.carousel_analysis

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.app.carousel.R
import com.app.carousel.databinding.CarouselAnalysisBottomSheetBinding
import com.app.carousel.domain.model.CarouselAnalysis
import com.app.carousel.domain.model.ResourceState
import com.app.carousel.presentation.CarouselViewModel
import com.app.carousel.utils.gone
import com.app.carousel.utils.visible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CarouselAnalysisBottomSheet : BottomSheetDialogFragment() {

    private var _binding: CarouselAnalysisBottomSheetBinding? = null
    private val binding: CarouselAnalysisBottomSheetBinding get() = _binding!!

    private val carouselViewModel: CarouselViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CarouselAnalysisBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                carouselViewModel.carouselAnalysis.collect {
                    when (it.status) {
                        ResourceState.LOADING -> {
                            binding.groupDataContent.gone()
                            binding.tvLoadingErrorStatus.visible()
                            binding.tvLoadingErrorStatus.text =
                                it.error ?: getString(R.string.txt_loading)
                        }

                        ResourceState.SUCCESS -> {
                            binding.groupDataContent.visible()
                            binding.tvLoadingErrorStatus.gone()
                            onDataReceived(it.data!!)
                        }

                        ResourceState.ERROR -> {
                            binding.groupDataContent.gone()
                            binding.tvLoadingErrorStatus.visible()
                            binding.tvLoadingErrorStatus.text =
                                it.error ?: getString(R.string.txt_error_occurred)
                        }
                    }
                }
            }
        }
    }

    private fun onDataReceived(data: CarouselAnalysis) {
        with(binding) {
            tvItemCount.text = getString(R.string.txt_item_count, data.carouselType, data.itemCount)
            val sb = StringBuilder()
            data.characterOccurrences.map { character ->
                sb.append(
                    getString(
                        R.string.list_character_count,
                        character.key,
                        character.value.toString()
                    )
                )
            }
            tvContent1.text = sb
        }
        Log.d("CarouselAnalysis", "onDataReceived: $data")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "CatalogAnalysisBottomSheet"
    }
}