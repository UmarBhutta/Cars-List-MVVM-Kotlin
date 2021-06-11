package com.capricorn.carslist.data.api

import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.api.service.ApiService
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.utils.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class NetworkData @Inject constructor(val apiService: ApiService,val networkConnectivity: NetworkConnectivity):NetworkDataSource {
    override suspend fun requestCarList(): Flow<Resource<List<Car>>> {
        TODO("Not yet implemented")
    }
}