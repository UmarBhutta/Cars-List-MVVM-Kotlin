package com.capricorn.carslist.data.error

import java.lang.Exception

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class Error(val code:Int,val description:String) {
    constructor(exception: Exception) : this(code = DEFAULT_ERROR,description = exception.message ?: "")
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3