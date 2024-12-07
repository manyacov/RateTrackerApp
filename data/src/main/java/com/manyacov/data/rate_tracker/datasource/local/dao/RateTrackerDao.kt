package com.manyacov.data.rate_tracker.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity
import androidx.room.Query

@Dao
interface RateTrackerDao {

    @Insert
    suspend fun saveCurrencySymbolsList(list: List<SymbolsEntity>)

    @Query("SELECT * FROM symbols")
    suspend fun getCurrencySymbolsList(): List<SymbolsEntity>

    @Query("SELECT * FROM symbols LIMIT 1")
    suspend fun getLastUpdated(): SymbolsEntity?
}