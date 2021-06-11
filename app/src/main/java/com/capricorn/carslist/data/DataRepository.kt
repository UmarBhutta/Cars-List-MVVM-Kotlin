package com.capricorn.carslist.data

import com.capricorn.carslist.data.api.NetworkData
import com.capricorn.carslist.data.dto.Car
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class DataRepository constructor(private val networkData: NetworkData,private val ioDispatcher:CoroutineContext):DataRepositorySource {

    override suspend fun requestCarList(): Flow<Resource<List<Car>>> {
        TODO("Not yet implemented")
    }

}