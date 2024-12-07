package com.manyacov.domain.rate_tracker.repository

import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.utils.CustomResult

interface RateTrackerRepository {

    suspend fun getCurrencySymbols(): CustomResult<List<CurrencySymbols>?>

    suspend fun getLatestRates(base: String): CustomResult<List<CurrencyRateValue>?>
}