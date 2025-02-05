package com.manyacov.domain.rate_tracker.repository

import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.domain.rate_tracker.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface RateTrackerRepository {

    suspend fun getCurrencySymbols(): Flow<CustomResult<List<CurrencySymbols>?>>

    suspend fun loadLatestRates(base: String, withSync: Boolean): Flow<CustomResult<List<CurrencyRateValue>>?>

    suspend fun getFavoriteRates(): Flow<CustomResult<List<FavoriteRatesValue>?>>

    suspend fun changeFavoriteStatus(base: String, symbols: String)
}