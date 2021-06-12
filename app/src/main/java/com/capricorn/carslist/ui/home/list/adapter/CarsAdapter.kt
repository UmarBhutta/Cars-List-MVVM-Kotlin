package com.capricorn.carslist.ui.home.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.databinding.CarListItemBinding
import com.capricorn.carslist.ui.home.list.ListFragmentViewModel

/**
 * Created by Muhammad Umar on 12/06/2021.
 */
class CarsAdapter(private val listFragmentViewModel: ListFragmentViewModel,private val cars:List<Car>) : RecyclerView.Adapter<CarViewHolder>() {

    private val onItemClickListener:RecyclerItemClickListener = object :RecyclerItemClickListener{
        override fun onCarItemClicked(car: Car) {
            listFragmentViewModel.openCarDetails(car)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemBinding = CarListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position],onItemClickListener)
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}