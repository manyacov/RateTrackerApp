package com.manyacov.presentation.all_rates

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
            state.value.filterOption.toString(),
            withSync
        ).collect { result ->
            when (result) {
                is CustomResult.Success -> {
                    _state.update {
                        state.value.copy(
                            ratesList = result.data ?: emptyList(),
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
        when (val result =
            repository.changeFavoriteStatus(state.value.baseSymbols?.symbols ?: "", symbols)) {

            is CustomResult.Success -> {
                getLatestRates(false)
            }

            is CustomResult.Error -> {
                _state.update {
                    state.value.copy(isLoading = false, error = result.issueType)
                }
            }
        }
    }
}