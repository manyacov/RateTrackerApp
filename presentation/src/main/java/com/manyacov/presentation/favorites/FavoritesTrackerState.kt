package com.manyacov.presentation.favorites

import androidx.compose.runtime.Immutable
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue

@Immutable
data class FavoritesTrackerState(
    val listFavorites: List<FavoriteRatesValue>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)