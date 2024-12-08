package com.manyacov.data.rate_tracker.repository

import android.util.Log
import com.manyacov.data.rate_tracker.datasource.local.RateTrackerDatabase
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import com.manyacov.data.rate_tracker.datasource.remote.api.RateTrackerApi
import com.manyacov.data.rate_tracker.mapper.toDomainModel
import com.manyacov.data.rate_tracker.mapper.toEntityModels
import com.manyacov.data.rate_tracker.mapper.toDomainModels
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

                val result = rateTrackerApi.getCurrencySymbols()
                val entities = result.toEntityModels()
                localSource.rateTrackerDao.saveCurrencySymbolsList(entities)
                getCashedSymbols().map { it.toDomainModel() }
            }
        }
    }

    private suspend fun getCashedSymbols(): List<SymbolsEntity> {
        return localSource.rateTrackerDao.getCurrencySymbolsList()
    }

    override suspend fun getLatestRates(base: String): CustomResult<List<CurrencyRateValue>?> {
        return safeCall {
            val result = rateTrackerApi.getLatestRates(base)

            val entities = result.toEntityModels()
            localSource.rateTrackerDao.saveRatesList(entities)

            val cached = localSource.rateTrackerDao.getRateList()
            cached.map { it.toDomainModels() }
        }
    }


    override suspend fun getFavoriteRates(): CustomResult<List<FavoriteRatesValue>?> {
        return safeCall {
            val pairs = localSource.rateTrackerDao.getFavoriteRatesList()
            return@safeCall if (pairs.isNotEmpty()) {
                val result = pairs.map {
                    rateTrackerApi.getLatestRatesForPair(it.baseSymbols, it.symbols)
                }
                result.map {
                    it.toDomainModel()
                }
            } else {
                emptyList<FavoriteRatesValue>()
            }
        }
    }

    override suspend fun changeFavoriteStatus(symbols: String): CustomResult<Unit?> {
        return safeCall {
            val db = localSource.rateTrackerDao

            val rateEntity = db.getRateEntityBySymbols(symbols)
            db.updateRateEntity(symbols, !rateEntity.isFavorite)
        }
    }
}