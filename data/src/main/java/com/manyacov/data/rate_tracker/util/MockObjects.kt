package com.manyacov.data.rate_tracker.util

import com.manyacov.data.rate_tracker.datasource.remote.model.RatesDto
import com.manyacov.data.rate_tracker.datasource.remote.model.SymbolsDto
import kotlin.random.Random

val mockSymbolsDto = SymbolsDto(
    success = true,
    symbols = mapOf(
        "USD" to "United States Dollar",
        "EUR" to "Euro",
        "JPY" to "Japanese Yen",
        "GBP" to "British Pound",
        "AUD" to "Australian Dollar",
        "CAD" to "Canadian Dollar",
        "CHF" to "Swiss Franc",
        "CNY" to "Chinese Yuan",
        "NZD" to "New Zealand Dollar",
        "SEK" to "Swedish Krona",
        "INR" to "Indian Rupee",
        "RUB" to "Russian Ruble",
        "SGD" to "Singapore Dollar",
        "HKD" to "Hong Kong Dollar",
        "MXN" to "Mexican Peso",
        "ZAR" to "South African Rand",
        "BRL" to "Brazilian Real",
        "PLN" to "Polish Zloty",
        "THB" to "Thai Baht",
        "AED" to "United Arab Emirates Dirham"
    )
)

val ratesDtoUSD = RatesDto(
    base = "USD",
    date = "2023-01-01",
    rates = mapOf(
        "EUR" to 0.85,
        "JPY" to 110.0,
        "GBP" to 0.75,
        "AUD" to 1.35,
        "CAD" to 1.25,
        "CHF" to 0.92,
        "CNY" to 6.45,
        "NZD" to 1.4,
        "SEK" to 8.5,
        "INR" to 73.0,
        "RUB" to 75.0,
        "SGD" to 1.35,
        "HKD" to 7.8,
        "MXN" to 20.0,
        "ZAR" to 15.0,
        "BRL" to 5.25,
        "PLN" to 3.8,
        "THB" to 32.0,
        "AED" to 3.67,
        "TRY" to 14.0
    ),
    success = true,
    timestamp = System.currentTimeMillis()
)

val ratesDtoEUR = RatesDto(
    base = "EUR",
    date = "2023-01-01",
    rates = mapOf(
        "USD" to 1.18,
        "JPY" to 129.5,
        "GBP" to 0.88,
        "AUD" to 1.59,
        "CAD" to 1.47,
        "CHF" to 1.08,
        "CNY" to 7.59,
        "NZD" to 1.65,
        "SEK" to 10.0,
        "INR" to 86.0,
        "RUB" to 88.0,
        "SGD" to 1.59,
        "HKD" to 9.23,
        "MXN" to 23.5,
        "ZAR" to 17.5,
        "BRL" to 6.18,
        "PLN" to 4.5,
        "THB" to 37.5,
        "AED" to 4.31,
        "TRY" to 16.5
    ),
    success = true,
    timestamp = System.currentTimeMillis()
)

val ratesDtoJPY = RatesDto(
    base = "JPY",
    date = "2023-01-01",
    rates = mapOf(
        "USD" to 0.0091,
        "EUR" to 0.0077,
        "GBP" to 0.0068,
        "AUD" to 0.0123,
        "CAD" to 0.0114,
        "CHF" to 0.0084,
        "CNY" to 0.059,
        "NZD" to 0.0132,
        "SEK" to 0.0078,
        "INR" to 0.066,
        "RUB" to 0.067,
        "SGD" to 0.0123,
        "HKD" to 0.0071,
        "MXN" to 0.0182,
        "ZAR" to 0.0135,
        "BRL" to 0.0047,
        "PLN" to 0.0039,
        "THB" to 0.029,
        "AED" to 0.0001,
        "TRY" to 0.0007
    ),
    success = true,
    timestamp = System.currentTimeMillis()
)

fun generateSelectedRates(base: String?, symbols: String): RatesDto {
    val rates = symbols.split(", ").associateWith { generateRandomDouble(0.0, 100.0) }
    return RatesDto(
        base = base ?: "",
        date = "2023-01-01",
        rates = rates,
        success = true,
        timestamp = System.currentTimeMillis(),
    )
}

fun generateRandomDouble(min: Double, max: Double): Double {
    require(min < max) { "Минимальное значение должно быть меньше максимального." }
    return Random.nextDouble(min, max)
}