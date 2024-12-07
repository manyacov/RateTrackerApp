package com.manyacov.data.rate_tracker.datasource.remote.api

import com.manyacov.data.rate_tracker.datasource.remote.model.RatesDto
import com.manyacov.data.rate_tracker.datasource.remote.model.SymbolsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RateTrackerApi {

    @GET("symbols")
    suspend fun getCurrencySymbols(): SymbolsDto

    @GET("latest")
    suspend fun getLatestRates(@Query("base") base: String): RatesDto
}