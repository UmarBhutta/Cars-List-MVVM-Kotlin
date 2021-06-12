package com.capricorn.carslist.di

import android.content.Context
import com.capricorn.carslist.data.DataRepository
import com.capricorn.carslist.data.DataRepositorySource
import com.capricorn.carslist.data.api.NetworkData
import com.capricorn.carslist.data.api.NetworkDataSource
import com.capricorn.carslist.utils.FuelSpecification
import com.capricorn.carslist.utils.FuelUtils
import com.capricorn.carslist.utils.TransmissionSpecification
import com.capricorn.carslist.utils.TransmissionUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideNetworkData(networkData: NetworkData) : NetworkDataSource

    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepository) : DataRepositorySource

}