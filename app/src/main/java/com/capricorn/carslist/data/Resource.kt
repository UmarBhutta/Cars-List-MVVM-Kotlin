package com.capricorn.carslist.data

/**
 * Created by Muhammad Umar on 11/06/2021.
 * A generic class contains data, and status of process and error details in case of failure
 */
sealed class Resource<T> (
    val data:T? = null,
    val errorCode:Int? = null){

    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null ) : Resource<T>(data)
    class Error<T>(errorCode: Int?) : Resource<T>(errorCode = errorCode)


}