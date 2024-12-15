package com.manyacov.presentation.favorites

import androidx.compose.runtime.Immutable
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.domain.rate_tracker.utils.NetworkIssues

@Immutable
data class FavoritesTrackerState(
    val listFavorites: List<FavoriteRatesValue> = emptyList(),
    val isLoading: Boolean = false,
    val error: NetworkIssues? = null
)