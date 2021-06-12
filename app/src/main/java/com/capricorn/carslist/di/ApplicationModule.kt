package com.capricorn.carslist.di

import android.content.Context
import com.capricorn.carslist.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammad Umar on 11/06/2021.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkUtils(context)
    }

    @Provides
    @Singleton
    fun provideFuelSpecification(@ApplicationContext context: Context) : FuelSpecification{
        return FuelUtils(context)
    }

    @Provides
    @Singleton
    fun provideTransmissionSpecification(@ApplicationContext context: Context) : TransmissionSpecification{
        return TransmissionUtils(context)
    }

    @Provides
    @Singleton
    fun provideVehicleCleanliness(@ApplicationContext context: Context) : VehicleCleanliness{
        return InteriorUtils(context)
    }

}