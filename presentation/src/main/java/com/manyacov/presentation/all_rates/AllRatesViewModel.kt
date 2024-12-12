package com.manyacov.presentation.all_rates

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import com.manyacov.presentation.filter.SortOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    var state by mutableStateOf(RateTrackerState(isLoading = true))
        private set

    fun updateFilterOption(option: SortOptions) {
        state = state.copy(filterOption = option)
    }

    fun updateSelectedSymbols(symbols: CurrencySymbols) {
        state = state.copy(baseSymbols = symbols)
        getLatestRates(true)
    }

    fun getCurrencySymbols() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getCurrencySymbols()) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", result.data.toString())
                val base = state.baseSymbols
                state = state.copy(
                    symbols = result.data ?: listOf(CurrencySymbols("")),
                    baseSymbols = base ?: result.data?.get(0),
                    error = null
                )
                getLatestRates(base == null)
            }

            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                state = state.copy(
                    symbols = listOf(CurrencySymbols("")),
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }

    private fun getLatestRates(withSync: Boolean) = viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)

            Log.println(Log.ERROR, "KKKKKK", withSync.toString())

            when (val result = repository.loadLatestRates(state.baseSymbols?.symbols ?: "", state.filterOption.toString(), withSync)) {
                is CustomResult.Success -> {
                    Log.println(Log.ERROR, "SSSS", result.data.toString())

                    state = state.copy(
                        ratesList = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is CustomResult.Error -> {
                    Log.println(Log.ERROR, "SSSS_n", "")

                    state = state.copy(
                        isLoading = false,
                        error = result.issueType
                    )
                }
            }
        }

    fun selectFavorite(symbols: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.changeFavoriteStatus(state.baseSymbols?.symbols ?: "", symbols)) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", "selectFavorite")

                getLatestRates(false)
            }

            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                state = state.copy(
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }
}