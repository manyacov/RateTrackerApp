package com.manyacov.ratetrackerapp.di

import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//    @Binds
//    @Singleton
//    abstract fun bindRateTrackerRepository(rateTrackerRepositoryImpl: RateTrackerRepositoryImpl): RateTrackerRepository
//}