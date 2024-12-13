package com.manyacov.presentation.all_rates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import com.manyacov.presentation.filter.SortOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    var state by mutableStateOf(RateTrackerState(isLoading = true))
        private set

    private val _newState = MutableStateFlow<List<CurrencyRateValue>?>(listOf())
    val newState: StateFlow<List<CurrencyRateValue>?> = _newState.asStateFlow()

    fun updateFilterOption(option: SortOptions) {
        state = state.copy(filterOption = option)
    }

    fun updateSelectedSymbols(symbols: CurrencySymbols) = viewModelScope.launch(Dispatchers.IO) {
        state = state.copy(baseSymbols = symbols)
        getLatestRates(true)
    }

    fun getCurrencySymbols() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getCurrencySymbols()) {
            is CustomResult.Success -> {
                val base = state.baseSymbols
                state = state.copy(
                    symbols = result.data ?: listOf(CurrencySymbols("")),
                    baseSymbols = base ?: result.data?.get(0),
                    error = null
                )
                getLatestRates(base == null)
            }

            is CustomResult.Error -> {
                state = state.copy(
                    symbols = listOf(CurrencySymbols("")),
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }

    private suspend fun getLatestRates(withSync: Boolean) {
        repository.loadLatestRates(
            state.baseSymbols?.symbols ?: "",
            state.filterOption.toString(),
            withSync
        ).collect { result ->
            val res = when (result) {
                is CustomResult.Success -> {
                    result.data
                }
                is CustomResult.Error -> {
                    emptyList()
                }

                else -> emptyList()
            }
            _newState.emit(res)
        }
    }

    fun selectFavorite(symbols: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result =
            repository.changeFavoriteStatus(state.baseSymbols?.symbols ?: "", symbols)) {
            is CustomResult.Success -> {
                getLatestRates(false)
            }

            is CustomResult.Error -> {
                state = state.copy(
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }
}