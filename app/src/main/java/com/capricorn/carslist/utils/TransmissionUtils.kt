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
class TransmissionUtils @Inject constructor(@ApplicationContext val context: Context) : TransmissionSpecification {

    override fun getTransmissionIcon(transmissionType:String) : Drawable?{
        return when(transmissionType){
            "A" -> AppCompatResources.getDrawable(context,R.drawable.fuse)
            "M" -> AppCompatResources.getDrawable(context,R.drawable.car_shift_pattern)
            else -> AppCompatResources.getDrawable(context,R.drawable.fuse)
        }
    }

    override fun getTransmissionName(transmissionType: String): String {
        return when(transmissionType){
            "A" -> context.getString(R.string.automatic_transmission)
            "M" -> context.getString(R.string.manual_transmission)
            else -> context.getString(R.string.manual_transmission)
        }
    }
}

interface TransmissionSpecification{
    fun getTransmissionIcon(transmissionType:String) : Drawable?
    fun getTransmissionName(transmissionType:String) : String
}