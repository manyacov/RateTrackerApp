package com.manyacov.data.rate_tracker.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatesDto(
    @SerialName("base")
    val base: String,
    @SerialName("date")
    val date: String,
    @SerialName("rates")
    val rates: Map<String, Double>,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timestamp")
    val timestamp: Long,
)