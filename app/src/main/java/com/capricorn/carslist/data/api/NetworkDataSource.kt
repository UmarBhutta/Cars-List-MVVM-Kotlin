package com.capricorn.carslist.data.api

import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
interface NetworkDataSource {
    suspend fun requestCarList(): Resource<List<Car>>
}