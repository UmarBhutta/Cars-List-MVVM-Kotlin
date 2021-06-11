package com.capricorn.carslist.data

import com.capricorn.carslist.data.api.NetworkData
import com.capricorn.carslist.data.dto.Car
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
class DataRepository @Inject constructor(private val networkData: NetworkData,private val ioDispatcher:CoroutineContext):DataRepositorySource {

    override suspend fun requestCarList(): Flow<Resource<List<Car>>> {
       return flow {
           emit(networkData.requestCarList())
       }.flowOn(ioDispatcher)
    }

}