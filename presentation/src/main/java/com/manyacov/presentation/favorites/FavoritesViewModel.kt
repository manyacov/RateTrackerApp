package com.manyacov.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class FavoritesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesTrackerState())
    val state = _state
        .onStart {
            getFavoritesList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            FavoritesTrackerState(isLoading = true, error = null)
        )

    private fun getFavoritesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getFavoriteRates()) {
            is CustomResult.Success -> {

                Log.println(Log.ERROR, "SSSS_s", result.data.toString())

                _state.update {
                    it.copy(
                        listFavorites = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }

            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")

                _state.update {
                    it.copy(
                        listFavorites = listOf(),
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun removeFavoritePair(baseSymbols: String, symbols: String) =
        viewModelScope.launch(Dispatchers.IO) {
            removeFavorite(baseSymbols, symbols)
        }

    private suspend fun removeFavorite(baseSymbols: String, symbols: String) {
        when (repository.removeFavoritePair(base = baseSymbols, symbols = symbols)) {
            is CustomResult.Success -> {
                getFavoritesList()
            }

            is CustomResult.Error -> {
                Log.println(Log.ERROR, "SSSS_n", "")
            }
        }
    }
}