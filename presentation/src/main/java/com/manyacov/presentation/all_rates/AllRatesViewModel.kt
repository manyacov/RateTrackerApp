package com.manyacov.presentation.all_rates

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RateTrackerState())
    val state = _state
        .onStart {
            getCurrencySymbols()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RateTrackerState(isLoading = true, error = null)
        )

    private fun getCurrencySymbols() = viewModelScope.launch(Dispatchers.IO) {
        when(val result = repository.getCurrencySymbols()) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", result.data.toString())
                _state.update {
                    it.copy(
                        symbols = result.data ?: listOf(CurrencySymbols("")),
                        isLoading = false,
                        error = null
                    )
                }

                result.data?.first()?.let { getLatestRates(it.symbols) }
            }
            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                _state.update {
                    it.copy(
                        symbols = listOf(CurrencySymbols("")),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    private fun getLatestRates(base: String) = viewModelScope.launch(Dispatchers.IO) {
        when(val result = repository.getLatestRates(base)) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", result.data.toString())
                _state.update {
                    it.copy(
                        rates = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }
            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                _state.update {
                    it.copy(
                        rates = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun selectFavorite(symbols: String) = viewModelScope.launch(Dispatchers.IO) {
       // repository.changeFavoriteStatus(symbols)

        when(val result = repository.changeFavoriteStatus(symbols)) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", "selectFavorite")

                getLatestRates("AED")
            }
            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                _state.update {
                    it.copy(
                        rates = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}