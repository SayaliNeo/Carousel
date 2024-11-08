package com.app.carousel.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.carousel.data.model.CarouselList
import com.app.carousel.databinding.CarouselListItemBinding

class CarouselListAdapter :
    ListAdapter<CarouselList, CarouselListAdapter.ViewHolder>(CarouselListDiff()) {

    inner class ViewHolder(private val binding: CarouselListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: CarouselList) {
            with(binding) {
                ivThumbnail.setBackgroundResource(data.carouselImage)
                tvTitle.text = data.title
                tvSubtitle.text = data.description
            }
        }

    }

    class CarouselListDiff : DiffUtil.ItemCallback<CarouselList>() {
        override fun areItemsTheSame(oldItem: CarouselList, newItem: CarouselList): Boolean {
            return oldItem.title.contentEquals(newItem.title) && oldItem.description.contentEquals(
                newItem.description
            )
        }

        override fun areContentsTheSame(
            oldItem: CarouselList,
            newItem: CarouselList
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CarouselListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).run { holder.bindData(this) }
    }
}