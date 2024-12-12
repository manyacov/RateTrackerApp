package com.manyacov.domain.rate_tracker.repository

import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.domain.rate_tracker.utils.CustomResult

interface RateTrackerRepository {

    suspend fun getCurrencySymbols(): CustomResult<List<CurrencySymbols>?>

    suspend fun loadLatestRates(base: String, filterType: String?, withSync: Boolean): CustomResult<List<CurrencyRateValue>?>

    suspend fun getFavoriteRates(): CustomResult<List<FavoriteRatesValue>?>

    suspend fun changeFavoriteStatus(base: String, symbols: String): CustomResult<Unit?>

    suspend fun removeFavoritePair(base: String, symbols: String): CustomResult<Unit?>
}