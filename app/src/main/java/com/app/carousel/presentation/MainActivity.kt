package com.app.carousel.presentation


import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.app.carousel.R
import com.app.carousel.base.BaseActivity
import com.app.carousel.databinding.ActivityMainBinding
import com.app.carousel.presentation.adapter.CarouselImageAdapter
import com.app.carousel.presentation.adapter.CarouselListAdapter
import com.app.carousel.presentation.carousel_analysis.CarouselAnalysisBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.carouselImage.collectLatest {
                        carouselImageAdapter.submitList(it)
                    }
                }
                launch {
                    viewModel.carouselList.collectLatest {
                        carouselListAdapter.submitList(it)
                        with(binding.tvCatalogRangeStatus) {
                            isVisible = it.isNullOrEmpty()
                            text =
                                if (it == null) getString(R.string.txt_loading) else getString(R.string.txt_no_item_found)
                        }
                    }
                }
                launch {
                    viewModel.searchQuery.collectLatest {
                        binding.searchText.setQuery(it, false)
                    }
                }
                launch {
                    viewModel.showBottomSheet.collectLatest {
                        val analysisSheet = CarouselAnalysisBottomSheet()
                        analysisSheet.show(supportFragmentManager, CarouselAnalysisBottomSheet.TAG)
                    }
                }
            }
        }
    }

    override fun initialize() {
        with(binding) {
            viewpagerCarousel.adapter = carouselImageAdapter
            TabLayoutMediator(
                tabLayoutDotIndicator,
                viewpagerCarousel
            ) { _, _ -> }.attach()

            rvCarouselSubItemList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvCarouselSubItemList.adapter = carouselListAdapter

            searchText.setOnQueryTextListener(searchTextListener)
        }
        setupOnClickListeners()
    }

    private val viewModel by viewModels<CarouselViewModel>()

    private val carouselImageAdapter = CarouselImageAdapter()
    private val carouselListAdapter = CarouselListAdapter()

    private val searchTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            binding.searchText.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { viewModel.onSearchValueChange(newText) }
            return true
        }

    }

    private val onCarouselPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.searchText.clearFocus()
            viewModel.onCarouselChanged(position)
        }
    }

    private fun setupOnClickListeners() {
        binding.fbShowOptions.setOnClickListener {
            viewModel.showBottomSheet()
        }
        with(binding.searchText) {
            setOnClickListener {
                isIconified = false
                requestFocus()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.viewpagerCarousel.registerOnPageChangeCallback(onCarouselPageChanged)
    }

    override fun onPause() {
        super.onPause()
        binding.viewpagerCarousel.unregisterOnPageChangeCallback(onCarouselPageChanged)
    }
}
