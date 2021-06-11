package com.capricorn.carslist.data.api.service

import com.capricorn.carslist.data.dto.Car
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
interface ApiService {

    @GET("/codingtask/cars")
    suspend fun getCarList() : Flow<List<Car>>

}