package com.capricorn.carslist.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.capricorn.carslist.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 12/06/2021.
 */
class FuelUtils @Inject constructor(@ApplicationContext val context: Context) : FuelSpecification {

    override fun getFuelTypeIcon(type:String?): Drawable? {
        return when(type){
            "P" -> AppCompatResources.getDrawable(context,R.drawable.outline_local_gas_station_24)
            "D" -> AppCompatResources.getDrawable(context,R.drawable.outline_local_gas_station_24)
            "E" -> AppCompatResources.getDrawable(context,R.drawable.outline_battery_std_24)
            else -> AppCompatResources.getDrawable(context,R.drawable.outline_local_gas_station_24)
        }
    }
}

interface FuelSpecification{
    fun getFuelTypeIcon(type:String?): Drawable?
}