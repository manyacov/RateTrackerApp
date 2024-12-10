package com.manyacov.presentation.all_rates

import androidx.compose.runtime.Immutable
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue

@Immutable
data class RateTrackerState(
    val symbols: List<CurrencySymbols> = listOf(CurrencySymbols("")),
    val rates: List<CurrencyRateValue>? = null,
    val ratesPagedData: List<CurrencyRateValue>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)