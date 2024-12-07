package com.manyacov.data.rate_tracker.mapper

import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import com.manyacov.data.rate_tracker.datasource.remote.model.RatesDto
import com.manyacov.data.rate_tracker.datasource.remote.model.SymbolsDto
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

fun getCurrentUtcDate(): Date {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    return calendar.time
}
fun SymbolsDto.toEntityModels(): List<SymbolsEntity> {
    return this.symbols.map {
        SymbolsEntity(
            symbols = it.key,
            lastUpdate = getCurrentUtcDate()
        )
    }
}
fun SymbolsEntity.toDomainModel(): CurrencySymbols {
    return CurrencySymbols(
        symbols = this.symbols
    )
}

fun RatesDto.toDomainModels(): List<CurrencyRateValue> {
    return this.rates.map {
        CurrencyRateValue(
            symbol = it.key,
            value = it.value
        )
    }
}