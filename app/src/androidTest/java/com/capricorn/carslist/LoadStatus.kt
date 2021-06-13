package com.capricorn.carslist

/**
 * Created by Muhammad Umar on 13/06/2021.
 */
sealed class LoadStatus {
    object Success : LoadStatus()
    object Fail : LoadStatus()
    object EmptyResponse : LoadStatus()
}

