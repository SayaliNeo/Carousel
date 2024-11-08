package com.app.carousel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carousel.data.model.Carousel
import com.app.carousel.databinding.CarouselImageItemBinding

class CarouselImageAdapter :
    ListAdapter<Carousel, CarouselImageAdapter.ViewHolder>(CarouselDiff()) {

    inner class ViewHolder(private val binding: CarouselImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Carousel) {
            binding.root.setBackgroundResource(data.imageRes)
        }
    }

    class CarouselDiff : DiffUtil.ItemCallback<Carousel>() {
        override fun areItemsTheSame(oldItem: Carousel, newItem: Carousel): Boolean {
            return oldItem.type.ordinal == newItem.type.ordinal
        }

        override fun areContentsTheSame(
            oldItem: Carousel,
            newItem: Carousel
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CarouselImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).run { holder.bindData(this) }
    }
}