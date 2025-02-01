package com.manyacov.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.domain.rate_tracker.utils.CustomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: RateTrackerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesTrackerState(isLoading = true))
    val state: StateFlow<FavoritesTrackerState> = _state.asStateFlow()

    fun getFavoritesList() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { state.value.copy(isLoading = true) }
        repository.getFavoriteRates().collect { result ->
            when (result) {
                is CustomResult.Success -> {
                    _state.update {
                        it.copy(
                            listFavorites = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is CustomResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.issueType)
                    }
                }
            }
        }
    }

    fun removeFavoritePair(baseSymbols: String, symbols: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                state.value.copy(isLoading = true)
            }
            removeFavorite(baseSymbols, symbols)
        }

    private suspend fun removeFavorite(baseSymbols: String, symbols: String) {
        repository.changeFavoriteStatus(base = baseSymbols, symbols = symbols)
    }
}