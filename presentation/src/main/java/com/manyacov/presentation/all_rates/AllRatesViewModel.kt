package com.manyacov.presentation.all_rates

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import com.manyacov.presentation.filter.SortOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RateTrackerState(isLoading = true))
    val state: StateFlow<RateTrackerState> = _state.asStateFlow()

    fun updateFilterOption(option: SortOptions) {
        _state.update {
            state.value.copy(filterOption = option)
        }
    }

    fun updateSelectedSymbols(symbols: CurrencySymbols) = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            state.value.copy(baseSymbols = symbols)
        }
        getLatestRates(true)
    }

    fun getCurrencySymbols() = viewModelScope.launch(Dispatchers.IO) {
        repository.getCurrencySymbols().collect { result ->
            when (result) {
                is CustomResult.Success -> {
                    val base = state.value.baseSymbols
                    _state.update {
                        state.value.copy(
                            symbols = result.data ?: emptyList(),
                            baseSymbols = base ?: result.data?.get(0),
                            isLoading = false,
                            error = null
                        )
                    }
                    getLatestRates(base == null)
                }

                else -> {
                    _state.update {
                        state.value.copy(isLoading = false, error = result.issueType)
                    }
                }
            }
        }
    }

    fun reloadRates(withSync: Boolean = true) = viewModelScope.launch(Dispatchers.IO) {
        getLatestRates(withSync)
    }

    private suspend fun getLatestRates(withSync: Boolean) {
        _state.update { state.value.copy(isLoading = true) }
        repository.loadLatestRates(
            state.value.baseSymbols?.symbols ?: "",
            withSync
        ).collect { result ->
            when (result) {
                is CustomResult.Success -> {

                    val sortedList = when (state.value.filterOption) {
                        SortOptions.CODE_A_Z -> result.data?.sortedBy { it.symbols }
                        SortOptions.CODE_Z_A -> result.data?.sortedByDescending { it.symbols }
                        SortOptions.QUOTE_ASC -> result.data?.sortedBy { it.value }
                        SortOptions.QUOTE_DESC -> result.data?.sortedByDescending { it.value }
                    }.apply {
                        Log.println(Log.DEBUG, "RRRR", "filter")
                    }

                    _state.update {
                        state.value.copy(
                            ratesList = sortedList ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }

                else -> {
                    _state.update {
                        state.value.copy(isLoading = false, error = result?.issueType)
                    }
                }
            }
        }
    }

    fun selectFavorite(symbols: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.changeFavoriteStatus(state.value.baseSymbols?.symbols ?: "", symbols)
    }
}