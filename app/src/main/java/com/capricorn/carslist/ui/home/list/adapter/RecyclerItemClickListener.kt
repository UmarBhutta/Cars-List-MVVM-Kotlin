package com.capricorn.carslist.ui.home.list.adapter

import com.capricorn.carslist.data.dto.Car

/**
 * Created by Muhammad Umar on 12/06/2021.
 */
interface RecyclerItemClickListener {
    fun onCarItemClicked(car : Car)
}