package com.capricorn.carslist

import com.capricorn.carslist.LoadStatus.*
import com.capricorn.carslist.TestDataUtil.dataSuccess
import com.capricorn.carslist.TestDataUtil.initData
import com.capricorn.carslist.data.DataRepositorySource
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.data.error.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 13/06/2021.
 */
class TestDataRepository @Inject constructor() : DataRepositorySource {

    override suspend fun requestCarList(): Flow<Resource<List<Car>>> {
        return when(dataSuccess){
            Success ->{
                flow { emit(Resource.Success(initData()))  }
            }

            Fail -> {
                flow { emit(Resource.Error<List<Car>>(errorCode = NETWORK_ERROR)) }
            }

            EmptyResponse -> {
                flow { emit(Resource.Success<List<Car>>(emptyList())) } }


        }
    }
}