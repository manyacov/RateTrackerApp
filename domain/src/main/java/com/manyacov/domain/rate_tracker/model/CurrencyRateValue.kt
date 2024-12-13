package com.manyacov.domain.rate_tracker.model

data class CurrencyRateValue(
    val id: Int,
    val symbols: String,
    val value: Double?,
    val isFavorite: Boolean = false
)
