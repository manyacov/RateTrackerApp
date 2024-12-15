package com.manyacov.data.rate_tracker.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import androidx.room.Query
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.local.model.RateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RateTrackerDao {

    /**
     * Currency symbols list table.
     * Updated once a month.
     */
    @Insert
    suspend fun saveCurrencySymbolsList(list: List<SymbolsEntity>)

    @Query("SELECT * FROM symbols_table")
    fun getCurrencySymbolsList(): Flow<List<SymbolsEntity>>

    @Query("SELECT * FROM symbols_table LIMIT 1")
    fun getFirstElement(): SymbolsEntity?

    /**
     * All rates list.
     */
    @Insert
    suspend fun saveRatesList(list: List<RateEntity>)

    @Query("DELETE FROM rates_table")
    suspend fun clearRatesTable()

    @Query("SELECT * FROM rates_table ORDER BY symbols ASC")
    fun getRateListSortedBySymbolsAsc(): Flow<List<RateEntity>>

    @Query("SELECT * FROM rates_table ORDER BY symbols DESC")
    fun getRateListSortedBySymbolsDesc(): Flow<List<RateEntity>>

    @Query("SELECT * FROM rates_table ORDER BY value ASC")
    fun getRateListSortedByQuoteAsc(): Flow<List<RateEntity>>

    @Query("SELECT * FROM rates_table ORDER BY value DESC")
    fun getRateListSortedByQuoteDesc(): Flow<List<RateEntity>>

    @Query("SELECT * FROM favorites_table WHERE base_symbols = :base")
    suspend fun getFavoriteRatesListByBase(base: String): List<FavoritePairEntity>

    /**
     * isFavorite status changing.
     */
    @Query("SELECT * FROM rates_table WHERE symbols = :symbols")
    suspend fun getRateEntityBySymbols(symbols: String): RateEntity

    @Query("UPDATE rates_table SET is_favorite = :isFavorite WHERE symbols = :symbols")
    suspend fun updateRateEntity(symbols: String, isFavorite: Boolean)

    /**
     * Favorite list.
     */
    @Insert
    suspend fun saveFavoriteRatesEntity(favoriteEntity: FavoritePairEntity)

    @Query("DELETE FROM favorites_table WHERE base_symbols = :base AND symbols = :symbols")
    suspend fun removeFavoriteRatesEntity(base: String, symbols: String)

    @Query("SELECT * FROM favorites_table")
    suspend fun getFavoriteRatesList(): List<FavoritePairEntity>
}