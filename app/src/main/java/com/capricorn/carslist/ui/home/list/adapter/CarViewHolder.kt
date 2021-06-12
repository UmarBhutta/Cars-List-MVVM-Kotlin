package com.capricorn.carslist.ui.home.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.capricorn.carslist.R
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.databinding.CarListItemBinding
import com.capricorn.carslist.utils.loadImage

/**
 * Created by Muhammad Umar on 12/06/2021.
 */
class CarViewHolder(private val itemBinding: CarListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(carItem: Car, recyclerItemClickListener: RecyclerItemClickListener){
        itemBinding.carName.text = carItem.modelName
        itemBinding.carImage.loadImage(carItem.carImageUrl)
        itemBinding.root.setOnClickListener { recyclerItemClickListener.onCarItemClicked(carItem) }
    }

}