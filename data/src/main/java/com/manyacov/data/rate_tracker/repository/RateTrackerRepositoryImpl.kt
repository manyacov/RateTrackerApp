package com.manyacov.data.rate_tracker.repository

import com.manyacov.data.rate_tracker.datasource.local.RateTrackerDatabase
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RateTrackerRepositoryImpl @Inject constructor(
    private val rateTrackerApi: RateTrackerApi,
    private val localSource: RateTrackerDatabase
) : RateTrackerRepository {

    //TODO: refactor
    override suspend fun getCurrencySymbols(): Flow<CustomResult<List<CurrencySymbols>?>> {
        return flow {
            val safeResult = safeCall {
                val checkElement = localSource.rateTrackerDao.getFirstElement()
                if (checkElement != null && isLessThanOneMonthAgo(checkElement.lastUpdate)) {
                    localSource.rateTrackerDao.getCurrencySymbolsList()
                } else {
                    val result = mockSymbolsDto//rateTrackerApi.getCurrencySymbols()

                    val entities = result.toEntityModels()
                    localSource.rateTrackerDao.saveCurrencySymbolsList(entities)
                    localSource.rateTrackerDao.getCurrencySymbolsList()
                }
            }

            when (safeResult) {
                is CustomResult.Success -> {
                    safeResult.data?.collect { symbols ->
                        val domainModels = symbols.map { entity -> entity.toDomainModel() }
                        emit(CustomResult.Success(domainModels))
                    }
                }

                is CustomResult.Error -> {
                    emit(CustomResult.Error(issueType = safeResult.issueType))
                }
            }
        }
    }

    //TODO: refactor
    override suspend fun loadLatestRates(
        base: String,
        withSync: Boolean
    ): Flow<CustomResult<List<CurrencyRateValue>>?> {
        return flow {
            val safeResult = safeCall {
                if (withSync) {
//                    val result = rateTrackerApi.getLatestRates(base)
                    val result = when (base) {
                        "USD" -> ratesDtoUSD
                        "EUR" -> ratesDtoEUR
                        else -> ratesDtoJPY
                    }

                    val favoritesList =
                        localSource.rateTrackerDao.getFavoriteRatesListByBase(base)

                    val entities = result.rates.map { rate ->
                        rate.toEntityRateModel(
                            isFavorite = favoritesList.map { it.symbols }.contains(rate.key),
                            date = result.date
                        )
                    }

                    localSource.rateTrackerDao.clearRatesTable()
                    localSource.rateTrackerDao.saveRatesList(entities)
                }

                val db = localSource.rateTrackerDao
                db.getRateList()
            }

            when (safeResult) {
                is CustomResult.Success -> {
                    safeResult.data?.collect { rateEntities ->
                        val domainModels =
                            rateEntities.map { entity -> entity.toDomainModels() }
                        emit(CustomResult.Success(domainModels))
                    }
                }

                is CustomResult.Error -> {
                    emit(CustomResult.Error(issueType = safeResult.issueType))
                }
            }
        }
    }

    override suspend fun getFavoriteRates(): Flow<CustomResult<List<FavoriteRatesValue>?>> {
        return localSource.rateTrackerDao.getFavoriteRatesList().map { pairs ->
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
        val db = localSource.rateTrackerDao

        val rateEntity = db.getRateEntityBySymbols(symbols)
        if (rateEntity.isFavorite) {
            removeFavoritePair(base, symbols)
        } else {
            saveFavoritePair(base, symbols)
        }
        db.updateRateEntity(symbols, !rateEntity.isFavorite)
    }

    private suspend fun saveFavoritePair(base: String, symbols: String) {
        val db = localSource.rateTrackerDao
        val favoriteEntity = FavoritePairEntity(
            baseSymbols = base,
            symbols = symbols,
        )
        db.saveFavoriteRatesEntity(favoriteEntity)
    }

    private suspend fun removeFavoritePair(base: String, symbols: String) {
        val db = localSource.rateTrackerDao
        db.removeFavoriteRatesEntity(base, symbols)
    }
}