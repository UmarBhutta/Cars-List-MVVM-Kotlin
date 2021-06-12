package com.capricorn.carslist.utils

import android.content.Context
import com.capricorn.carslist.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 12/06/2021.
 */
class InteriorUtils @Inject constructor(@ApplicationContext val context: Context) : VehicleCleanliness{
    override fun getVehicleCleanliness(innerCleanliness: String): String {
        return when(innerCleanliness){
            "CLEAN" -> context.getString(R.string.clean)
            "VERY_CLEAN" -> context.getString(R.string.very_clean)
            "REGULAR" -> context.getString(R.string.regular)
            else -> context.getString(R.string.regular)
        }
    }
}

interface VehicleCleanliness{
    fun getVehicleCleanliness(innerCleanliness:String) : String
}