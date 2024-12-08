package com.manyacov.domain.rate_tracker.model

data class CurrencyRateValue(
    val symbols: String,
    val value: Double?,
    val isFavorite: Boolean = false
)
