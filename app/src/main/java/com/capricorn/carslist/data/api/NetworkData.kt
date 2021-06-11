package com.capricorn.carslist.data.api

import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.api.service.ApiService
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.data.error.NETWORK_ERROR
import com.capricorn.carslist.data.error.NO_INTERNET_CONNECTION
import com.capricorn.carslist.utils.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class NetworkData @Inject constructor(private val apiService: ApiService,private val networkConnectivity: NetworkConnectivity):NetworkDataSource {
    override suspend fun requestCarList(): Resource<List<Car>> {
        return when (val response = processCall(apiService::getCarList)) {
            is List<*> -> {
                Resource.Success(data = response as List<Car>)
            }
            else -> {
                Resource.Error(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}