package com.capricorn.carslist.data

import com.capricorn.carslist.data.dto.Car
import kotlinx.coroutines.flow.Flow


/**
 * Created by Muhammad Umar on 11/06/2021.
 */
interface DataRepositoryImpl {
    suspend fun requestCarList():Flow<Resource<List<Car>>>
}