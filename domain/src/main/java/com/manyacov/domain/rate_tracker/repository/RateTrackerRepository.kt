package com.manyacov.domain.rate_tracker.repository

import androidx.paging.PagingData
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.domain.rate_tracker.utils.CustomResult
import kotlinx.coroutines.flow.Flow

interface RateTrackerRepository {

    suspend fun getCurrencySymbols(): CustomResult<List<CurrencySymbols>?>

    suspend fun getLatestRates(base: String, filterType: String?): CustomResult<List<CurrencyRateValue>?>

    //suspend fun loadLatestRates(base: String, filterType: String?): CustomResult<PagingSource<Int, CurrencyRateValue>?>
    suspend fun loadLatestRates(base: String, filterType: String?): CustomResult<Flow<PagingData<CurrencyRateValue>>?>

    suspend fun getFavoriteRates(): CustomResult<List<FavoriteRatesValue>?>

    suspend fun changeFavoriteStatus(base: String, symbols: String): CustomResult<Unit?>

    suspend fun removeFavoritePair(base: String, symbols: String): CustomResult<Unit?>
}