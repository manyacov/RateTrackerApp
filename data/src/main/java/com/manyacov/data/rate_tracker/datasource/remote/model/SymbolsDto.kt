package com.manyacov.data.rate_tracker.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolsDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("symbols")
    val symbols: Map<String, String>,
)
