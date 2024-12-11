package com.manyacov.data.rate_tracker.mapper

import com.manyacov.data.rate_tracker.datasource.local.model.RateEntity
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import com.manyacov.data.rate_tracker.datasource.remote.model.RatesDto
import com.manyacov.data.rate_tracker.datasource.remote.model.SymbolsDto
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
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

//fun RatesDto.toEntityModels(): List<RateEntity> {
//    return this.rates.map {
//        RateEntity(
//            symbols = it.key,
//            value = it.value,
//            date = this.date,
//            isFavorite = false
//        )
//    }
//}

fun  Map.Entry<String, Double>.toEntityRateModel(isFavorite: Boolean, date: String): RateEntity {
    return RateEntity(
        symbols = this.key,
        value = this.value,
        date = date,
        isFavorite = isFavorite
    )
}

fun RateEntity.toDomainModels(): CurrencyRateValue {
    return CurrencyRateValue(
        symbols = this.symbols,
        value = this.value,
        isFavorite = this.isFavorite
    )
}

//fun RatesDto.toDomainModels(): List<CurrencyRateValue> {
//    return this.rates.map {
//        CurrencyRateValue(
//            symbols = it.key,
//            value = it.value
//        )
//    }
//}

fun RatesDto.toDomainModels(): List<FavoriteRatesValue> {
    return rates.map { (symbol, value) ->
        FavoriteRatesValue(
            baseSymbols = base,
            symbols = symbol,
            value = value
        )
    }
}