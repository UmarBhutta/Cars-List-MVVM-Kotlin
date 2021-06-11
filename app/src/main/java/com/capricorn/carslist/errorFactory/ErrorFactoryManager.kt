package com.capricorn.carslist.errorFactory

import com.capricorn.carslist.data.error.Error
import com.capricorn.carslist.data.error.mapper.ErrorMapper
import com.capricorn.carslist.data.error.mapper.Mapper
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 * Class to use simplified errors and their values
 */
class ErrorFactoryManager @Inject constructor(private val errorMapper: Mapper): ErrorFactory {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode,description = errorMapper.errorsMap.getValue(errorCode))
    }

}