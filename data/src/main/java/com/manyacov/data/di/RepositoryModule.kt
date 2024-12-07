package com.manyacov.data.di

import com.manyacov.data.rate_tracker.repository.RateTrackerRepositoryImpl
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRateTrackerRepository(rateTrackerRepositoryImpl: RateTrackerRepositoryImpl): RateTrackerRepository
}