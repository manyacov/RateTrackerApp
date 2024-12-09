package com.manyacov.data.rate_tracker.repository

import android.util.Log
import com.manyacov.data.rate_tracker.datasource.local.RateTrackerDatabase
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import com.manyacov.data.rate_tracker.datasource.remote.api.RateTrackerApi
import com.manyacov.data.rate_tracker.datasource.remote.model.RatesDto
import com.manyacov.data.rate_tracker.datasource.remote.model.SymbolsDto
import com.manyacov.data.rate_tracker.mapper.toDomainModel
import com.manyacov.data.rate_tracker.mapper.toEntityModels
import com.manyacov.data.rate_tracker.mapper.toDomainModels
import com.manyacov.data.rate_tracker.mapper.toEntityRateModel
import com.manyacov.data.rate_tracker.util.isLessThanOneMonthAgo
import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
import com.manyacov.data.rate_tracker.util.safeCall
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.domain.rate_tracker.utils.CustomResult
import javax.inject.Inject

class RateTrackerRepositoryImpl @Inject constructor(
    private val rateTrackerApi: RateTrackerApi,
    private val localSource: RateTrackerDatabase
) : RateTrackerRepository {

    override suspend fun getCurrencySymbols(): CustomResult<List<CurrencySymbols>?> {
        return safeCall {

            val cached = getCashedSymbols()
            if (cached.isNotEmpty() && isLessThanOneMonthAgo(cached[0].lastUpdate)) {
                Log.println(Log.ERROR, "YYY_cashed", "")
                cached.map { it.toDomainModel() }
            } else {
                Log.println(Log.ERROR, "YYY_remote", "")

                //val result = rateTrackerApi.getCurrencySymbols()
                val result = mockSymbols()

                val entities = result.toEntityModels()
                localSource.rateTrackerDao.saveCurrencySymbolsList(entities)
                getCashedSymbols().map { it.toDomainModel() }
            }
        }
    }

    private suspend fun getCashedSymbols(): List<SymbolsEntity> {
        return localSource.rateTrackerDao.getCurrencySymbolsList()
    }

    override suspend fun getLatestRates(
        base: String,
        filterType: String?
    ): CustomResult<List<CurrencyRateValue>?> {
        return safeCall {
            //val result = rateTrackerApi.getLatestRates(base)
            val result = mockLatestRates()

            val favoritesList = localSource.rateTrackerDao.getFavoriteRatesListByBase(base)

            val entities = result.rates.map { rate ->
                rate.toEntityRateModel(
                    isFavorite = favoritesList.map { it.symbols }.contains(rate.key),
                    date = result.date
                )
            }
            localSource.rateTrackerDao.clearRatesTable()
            localSource.rateTrackerDao.saveRatesList(entities)

            Log.println(Log.ERROR, "TTTT impl", filterType.toString())

            val cached = when (filterType) {
                null, "Code A-Z" -> {
                    localSource.rateTrackerDao.getRateListSortedBySymbolsAsc()
                }
                "Code Z-A" -> {
                    localSource.rateTrackerDao.getRateListSortedBySymbolsDesc()
                }
                "Quote Asc." -> {
                    localSource.rateTrackerDao.getRateListSortedByQuoteAsc()
                }
                else -> {
                    localSource.rateTrackerDao.getRateListSortedByQuoteDesc()
                }
            }
            cached.map { it.toDomainModels() }
        }
    }


    override suspend fun getFavoriteRates(): CustomResult<List<FavoriteRatesValue>?> {
        return safeCall {
            val pairs = localSource.rateTrackerDao.getFavoriteRatesList()
            return@safeCall if (pairs.isNotEmpty()) {
                val result = pairs.map {
                    mockLatestRatesByBaseEUR(it.baseSymbols, it.symbols)
                    //rateTrackerApi.getLatestRatesForPair(it.baseSymbols, it.symbols)
                }
                result.map {
                    it.toDomainModel()
                }
            } else {
                emptyList<FavoriteRatesValue>()
            }
        }
    }

    override suspend fun changeFavoriteStatus(base: String, symbols: String): CustomResult<Unit?> {
        return safeCall {
            val db = localSource.rateTrackerDao

            val rateEntity = db.getRateEntityBySymbols(symbols)
            if (rateEntity.isFavorite) {
                removeFavoritePair(base, symbols)
            } else {
                saveFavoritePair(base, symbols)
            }
            db.updateRateEntity(symbols, !rateEntity.isFavorite)
        }
    }

    private suspend fun saveFavoritePair(base: String, symbols: String): CustomResult<Unit?> {
        return safeCall {
            val db = localSource.rateTrackerDao
            val favoriteEntity = FavoritePairEntity(
                baseSymbols = base,
                symbols = symbols,
            )
            db.saveFavoriteRatesEntity(favoriteEntity)
        }
    }

    override suspend fun removeFavoritePair(base: String, symbols: String): CustomResult<Unit?> {
        return safeCall {
            val db = localSource.rateTrackerDao
            db.removeFavoriteRatesEntity(base, symbols)
        }
    }

    fun mockSymbols() = SymbolsDto(
        success = true,
        symbols = mapOf(
            "USD" to "Dollar",
            "EUR" to "Euro",
            "BYN" to "Belki"
        )
    )

    fun mockLatestRates() = RatesDto(
        base = "USD",
        date = "09.12.2024",
        rates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.2431,
            "BYN" to 3.5423
        ),
        success = true,
        timestamp = 524854725934
    )

    fun mockLatestRatesByBaseEUR(base: String?, symbols: String?) = RatesDto(
        base = base ?: "",
        date = "09.12.2024",
        rates = mapOf(
            (symbols ?: "") to 0.2431,
        ),
        success = true,
        timestamp = 524854725934
    )

    fun mockLatestRatesByBaseBYN() = RatesDto(
        base = "USD",
        date = "09.12.2024",
        rates = mapOf(
            "BYN" to 3.5423,
        ),
        success = true,
        timestamp = 524854725934
    )
}