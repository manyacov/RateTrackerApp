package com.manyacov.presentation.all_rates

import androidx.compose.runtime.Immutable
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.utils.NetworkIssues

@Immutable
data class RateTrackerState(
    val symbols: List<CurrencySymbols> = listOf(CurrencySymbols("")),
    val baseSymbols: String = "",
    val isLoading: Boolean = false,
    val error: NetworkIssues? = null
)