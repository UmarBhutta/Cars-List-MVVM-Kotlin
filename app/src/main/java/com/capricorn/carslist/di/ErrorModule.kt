package com.capricorn.carslist.di

import com.capricorn.carslist.data.error.mapper.ErrorMapper
import com.capricorn.carslist.usecase.error.ErrorFactoryManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {

    @Binds
    @Singleton
    abstract fun provideErrorFactoryManager(errorFactoryManager: ErrorFactoryManager) : ErrorFactoryManager

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper) : ErrorMapper

}