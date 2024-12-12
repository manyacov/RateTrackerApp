package com.manyacov.presentation.all_rates

import androidx.compose.runtime.Immutable
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.utils.NetworkIssues
import com.manyacov.presentation.filter.SortOptions

@Immutable
data class RateTrackerState(
    val symbols: List<CurrencySymbols> = listOf(CurrencySymbols("")),
    val baseSymbols: CurrencySymbols? = null,
    val filterOption: SortOptions = SortOptions.CODE_A_Z,
    val ratesList: List<CurrencyRateValue> = emptyList(),
    val isLoading: Boolean = false,
    val error: NetworkIssues? = null
)