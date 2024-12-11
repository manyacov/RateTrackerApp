package com.manyacov.data.rate_tracker.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun loadLatestRates(
        base: String,
        filterType: String?
    ): CustomResult<Flow<PagingData<CurrencyRateValue>>?> {
        return safeCall {
            //val result = rateTrackerApi.getLatestRates(base)
            val result = mockLatestRates(base)

            val favoritesList = localSource.rateTrackerDao.getFavoriteRatesListByBase(base)

            val entities = result.rates.map { rate ->
                rate.toEntityRateModel(
                    isFavorite = favoritesList.map { it.symbols }.contains(rate.key),
                    date = result.date
                )
            }
            localSource.rateTrackerDao.clearRatesTable()
            localSource.rateTrackerDao.saveRatesList(entities)


            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    when(filterType) {
                        "CODE_Z_A" -> localSource.rateTrackerDao.getRateListSortedBySymbolsDesc()
                        "QUOTE_ASC" -> localSource.rateTrackerDao.getRateListSortedByQuoteAsc()
                        "QUOTE_DESC" -> localSource.rateTrackerDao.getRateListSortedByQuoteDesc()
                        else -> localSource.rateTrackerDao.getRateListSortedBySymbolsAsc()
                    }
                }
            ).flow.map { pagingData ->
                pagingData.map { entity ->
                    entity.toDomainModels()
                }
            }
        }
    }


    override suspend fun getFavoriteRates(): CustomResult<List<FavoriteRatesValue>?> {
        return safeCall {
            val pairs = localSource.rateTrackerDao.getFavoriteRatesList()
            val favoritePairs = pairs
                .groupBy { it.baseSymbols }
                .mapValues { entry ->
                    entry.value.mapNotNull { it.symbols }
                }

            return@safeCall if (pairs.isNotEmpty()) {
                val result = favoritePairs.map {
                    rateTrackerApi.getLatestRatesForPair(it.key, it.value.joinToString())
                }

                result.flatMap { ratesDto ->
                    ratesDto.toDomainModels()
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

    fun mockLatestRates(base: String) = RatesDto(
        base = base,
        date = "09.12.2024",
        rates = mapOf(
            "USD" to 1.0,
        ).plus((1..1000).map { index ->
            val code = "CUR${index.toString().padStart(3, '0')}"
            val value = (1..100).random() / 10.0
            code to value
        }).toMap(),
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