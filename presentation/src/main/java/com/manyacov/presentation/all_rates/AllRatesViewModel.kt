package com.manyacov.presentation.all_rates

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import com.manyacov.presentation.filter.SortOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RateTrackerState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        RateTrackerState(isLoading = true, error = null)
    )

    private var _rateValues: Flow<PagingData<CurrencyRateValue>>? = null

    val rateValues: Flow<PagingData<CurrencyRateValue>>?
        get() = _rateValues


    fun getCurrencySymbols() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getCurrencySymbols()) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", result.data.toString())
                _state.update {
                    it.copy(
                        symbols = result.data ?: listOf(CurrencySymbols("")),
                        baseSymbols = result.data?.get(0)?.symbols ?: "",
                        isLoading = false,
                        error = null
                    )
                }
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

    fun getLatestRates(base: String, sortOption: SortOptions?) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(baseSymbols = base) }

            when (val result = repository.loadLatestRates(base, sortOption.toString())) {
                is CustomResult.Success -> {
                    Log.println(Log.ERROR, "SSSS", result.data.toString())
//                _state.update {
//                    it.copy(
//                        rates = result.data,
//                        isLoading = false,
//                        error = null
//                    )
//                }
                    _rateValues = result.data
                }

                is CustomResult.Error -> {
                    Log.println(Log.ERROR, "SSSS_n", "")

                    _state.update {
                        it.copy(
                            //rates = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }

//    fun applyFilter(base: String, filterType: String?) = viewModelScope.launch(Dispatchers.IO) {
//        Log.println(Log.ERROR, "TTTT", "applyFilter")
//
//        when(val result = repository.loadLatestRates(base, filterType)) {
//            is CustomResult.Success -> {
//                Log.println(Log.ERROR, "SSSS", result.data.toString())
////                _state.update {
////                    it.copy(
////                        rates = result.data,
////                        isLoading = false,
////                        error = null
////                    )
////                }
//                _rateValues = result.data
//            }
//            is CustomResult.Error -> {
//                Log.println(Log.ERROR, "SSSS_n", "")
//
//                _state.update {
//                    it.copy(
//                        rates = null,
//                        isLoading = false,
//                        error = result.message
//                    )
//                }
//            }
//        }
//    }

    fun selectFavorite(symbols: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.changeFavoriteStatus(state.value.baseSymbols, symbols)) {
            is CustomResult.Success -> {
                Log.println(Log.ERROR, "SSSS", "selectFavorite")

                getLatestRates(state.value.baseSymbols, null)
            }

            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                _state.update {
                    it.copy(
                        //rates = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}