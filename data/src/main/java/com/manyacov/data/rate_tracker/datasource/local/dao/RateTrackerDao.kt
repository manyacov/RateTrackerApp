package com.manyacov.data.rate_tracker.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import androidx.room.Query
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.local.model.RateEntity

@Dao
interface RateTrackerDao {

    @Insert
    suspend fun saveCurrencySymbolsList(list: List<SymbolsEntity>)

    @Query("SELECT * FROM symbols")
    suspend fun getCurrencySymbolsList(): List<SymbolsEntity>

    @Insert
    suspend fun saveRatesList(list: List<RateEntity>)

    @Query("SELECT * FROM rate_entity LIMIT 5")
    suspend fun getRateList(): List<RateEntity>

    @Query("SELECT * FROM rate_entity WHERE symbols = :symbols")
    suspend fun getRateEntityBySymbols(symbols: String): RateEntity
    @Query("UPDATE rate_entity SET is_favorite = :isFavorite WHERE symbols = :symbols")
    suspend fun updateRateEntity(symbols: String, isFavorite: Boolean)

    @Insert
    suspend fun saveFavoriteRatesList(list: List<FavoritePairEntity>)

    @Query("SELECT * FROM favorites")
    suspend fun getFavoriteRatesList(): List<FavoritePairEntity>
}