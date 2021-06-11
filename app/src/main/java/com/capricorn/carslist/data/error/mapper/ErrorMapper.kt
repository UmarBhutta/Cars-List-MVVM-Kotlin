package com.capricorn.carslist.data.error.mapper

import android.content.Context
import com.capricorn.carslist.R
import com.capricorn.carslist.data.error.NETWORK_ERROR
import com.capricorn.carslist.data.error.NO_INTERNET_CONNECTION
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) : Mapper {
    override fun getErrorString(stringResourceId: Int): String {
        return context.getString(stringResourceId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
                Pair(NO_INTERNET_CONNECTION,getErrorString(R.string.no_internet)),
                Pair(NETWORK_ERROR,getErrorString(R.string.network_error))
        ).withDefault { getErrorString(R.string.network_error) }
}