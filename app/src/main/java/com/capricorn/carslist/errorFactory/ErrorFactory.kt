package com.capricorn.carslist.usecase.error

import com.capricorn.carslist.data.error.Error

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
interface ErrorFactory {
    fun getError(errorCode:Int): Error
}