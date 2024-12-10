package com.manyacov.data.rate_tracker.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import androidx.room.Query
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.local.model.RateEntity

@Dao
interface RateTrackerDao {

    /**
     * Currency symbols list table.
     * Updated once a month.
     */
    @Insert
    suspend fun saveCurrencySymbolsList(list: List<SymbolsEntity>)

    @Query("SELECT * FROM symbols")
    suspend fun getCurrencySymbolsList(): List<SymbolsEntity>

    /**
     * All rates list.
     * Used mostly for pagination purposes.
     */
    @Insert
    suspend fun saveRatesList(list: List<RateEntity>)

    @Query("DELETE FROM rate_entity")
    suspend fun clearRatesTable()

//    @Query("SELECT * FROM rate_entity ORDER BY symbols ASC")
//    fun getPagingRateListSortedBySymbolsAsc(): PagingSource<Int, RateEntity>

    @Query("SELECT * FROM rate_entity ORDER BY symbols ASC")
    fun getRateListSortedBySymbolsAsc(): PagingSource<Int, RateEntity>

    @Query("SELECT * FROM rate_entity ORDER BY symbols DESC")
    fun getRateListSortedBySymbolsDesc(): PagingSource<Int, RateEntity>

    @Query("SELECT * FROM rate_entity ORDER BY value ASC")
    fun getRateListSortedByQuoteAsc(): PagingSource<Int, RateEntity>

    @Query("SELECT * FROM rate_entity ORDER BY value DESC")
    fun getRateListSortedByQuoteDesc(): PagingSource<Int, RateEntity>

    @Query("SELECT * FROM favorites WHERE base_symbols = :base")
    suspend fun getFavoriteRatesListByBase(base: String): List<FavoritePairEntity>

    /**
     * isFavorite status changing.
     */
    @Query("SELECT * FROM rate_entity WHERE symbols = :symbols")
    suspend fun getRateEntityBySymbols(symbols: String): RateEntity

    @Query("UPDATE rate_entity SET is_favorite = :isFavorite WHERE symbols = :symbols")
    suspend fun updateRateEntity(symbols: String, isFavorite: Boolean)

    /**
     * Favorite list.
     */
    @Insert
    suspend fun saveFavoriteRatesEntity(favoriteEntity: FavoritePairEntity)

    @Query("DELETE FROM favorites WHERE base_symbols = :base AND symbols = :symbols")
    suspend fun removeFavoriteRatesEntity(base: String, symbols: String)

    @Query("SELECT * FROM favorites")
    suspend fun getFavoriteRatesList(): List<FavoritePairEntity>
}