package com.manyacov.data.rate_tracker.repository

import com.manyacov.data.rate_tracker.datasource.local.dao.RateTrackerDao
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.remote.api.RateTrackerApi
import com.manyacov.data.rate_tracker.mapper.toDomainModel
import com.manyacov.data.rate_tracker.mapper.toEntityModels
import com.manyacov.data.rate_tracker.mapper.toDomainModels
import com.manyacov.data.rate_tracker.mapper.toEntityRateModel
import com.manyacov.data.rate_tracker.util.generateSelectedRates
import com.manyacov.data.rate_tracker.util.isLessThanOneMonthAgo
import com.manyacov.data.rate_tracker.util.mockSymbolsDto
import com.manyacov.data.rate_tracker.util.ratesDtoEUR
import com.manyacov.data.rate_tracker.util.ratesDtoJPY
import com.manyacov.data.rate_tracker.util.ratesDtoUSD
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
    private val localSource: RateTrackerDao
) : RateTrackerRepository {

    override suspend fun getCurrencySymbols(): Flow<CustomResult<List<CurrencySymbols>?>> {
        val checkElement = localSource.getFirstElement()
        return if (checkElement != null && isLessThanOneMonthAgo(checkElement.lastUpdate)) {
            localSource.getCurrencySymbolsList().map { symbols ->
                val domainModels = symbols.map { entity -> entity.toDomainModel() }
                CustomResult.Success(domainModels)
            }
        } else {
            val result = mockSymbolsDto//rateTrackerApi.getCurrencySymbols()

            val entities = result.toEntityModels()
            localSource.saveCurrencySymbolsList(entities)
            localSource.getCurrencySymbolsList().map { symbols ->
                val domainModels = symbols.map { entity -> entity.toDomainModel() }
                CustomResult.Success(domainModels)
            }
        }
    }

    override suspend fun loadLatestRates(
        base: String,
        withSync: Boolean
    ): Flow<CustomResult<List<CurrencyRateValue>>?> {
        if (withSync) {
//                    val result = rateTrackerApi.getLatestRates(base)
            val result = when (base) {
                "USD" -> ratesDtoUSD
                "EUR" -> ratesDtoEUR
                else -> ratesDtoJPY
            }

            val favoritesList = localSource.getFavoriteRatesListByBase(base)

            val entities = result.rates.map { rate ->
                rate.toEntityRateModel(
                    isFavorite = favoritesList.map { it.symbols }.contains(rate.key),
                    date = result.date
                )
            }

            localSource.clearRatesTable()
            localSource.saveRatesList(entities)
        }

        return localSource.getRateList().map { rates ->
            val domainModels = rates.map { entity -> entity.toDomainModels() }
            CustomResult.Success(domainModels)
        }
    }

    override suspend fun getFavoriteRates(): Flow<CustomResult<List<FavoriteRatesValue>?>> {
        return localSource.getFavoriteRatesList().map { pairs ->
            safeCall {
                val favoritePairs = pairs
                    .groupBy { it.baseSymbols }
                    .mapValues { entry ->
                        entry.value.mapNotNull { it.symbols }
                    }

                if (pairs.isNotEmpty()) {
                    favoritePairs
                        .map {
                            generateSelectedRates(it.key, it.value.joinToString())
                            //rateTrackerApi.getLatestRatesForPair(it.key, it.value.joinToString())
                        }
                        .flatMap { ratesDto -> ratesDto.toDomainModels() }
                } else {
                    emptyList()
                }
            }
        }
    }

    override suspend fun changeFavoriteStatus(base: String, symbols: String) {
        val rateEntity = localSource.getRateEntityBySymbols(symbols)
        if (rateEntity.isFavorite) {
            removeFavoritePair(base, symbols)
        } else {
            saveFavoritePair(base, symbols)
        }
        localSource.updateRateEntity(symbols, !rateEntity.isFavorite)
    }

    private suspend fun saveFavoritePair(base: String, symbols: String) {
        val favoriteEntity = FavoritePairEntity(
            baseSymbols = base,
            symbols = symbols,
        )
        localSource.saveFavoriteRatesEntity(favoriteEntity)
    }

    private suspend fun removeFavoritePair(base: String, symbols: String) {
        localSource.removeFavoriteRatesEntity(base, symbols)
    }
}