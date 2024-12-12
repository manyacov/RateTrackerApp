package com.manyacov.presentation.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    var state by mutableStateOf(FavoritesTrackerState(isLoading = true))
        private set

    fun getFavoritesList() = viewModelScope.launch(Dispatchers.IO) {
        when (val result = repository.getFavoriteRates()) {
            is CustomResult.Success -> {
                state = state.copy(
                    listFavorites = result.data,
                    isLoading = false,
                    error = null
                )
            }

            is CustomResult.Error -> {
                state = state.copy(
                    listFavorites = listOf(),
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }

    fun removeFavoritePair(baseSymbols: String, symbols: String) =
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            removeFavorite(baseSymbols, symbols)
        }

    private suspend fun removeFavorite(baseSymbols: String, symbols: String) {
        when (val result = repository.removeFavoritePair(base = baseSymbols, symbols = symbols)) {
            is CustomResult.Success -> {
                getFavoritesList()
            }

            is CustomResult.Error -> {
                state = state.copy(
                    listFavorites = listOf(),
                    isLoading = false,
                    error = result.issueType
                )
            }
        }
    }
}