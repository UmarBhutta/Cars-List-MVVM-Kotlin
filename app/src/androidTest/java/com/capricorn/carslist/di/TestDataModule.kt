package com.capricorn.carslist.di

import com.capricorn.carslist.TestDataRepository
import com.capricorn.carslist.data.DataRepositorySource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * Created by Muhammad Umar on 13/06/2021.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
abstract class TestDataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: TestDataRepository) : DataRepositorySource
}