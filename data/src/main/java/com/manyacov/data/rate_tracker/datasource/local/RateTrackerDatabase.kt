package com.manyacov.data.rate_tracker.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manyacov.data.rate_tracker.datasource.local.dao.RateTrackerDao
import com.manyacov.data.rate_tracker.datasource.local.model.FavoritePairEntity
import com.manyacov.data.rate_tracker.datasource.local.model.RateEntity
import com.manyacov.data.rate_tracker.datasource.local.model.SymbolsEntity

class RateTrackerDatabase internal constructor(private val database: RateTrackerRoomDatabase) {
    val rateTrackerDao: RateTrackerDao
        get() = database.rateTrackerDao()
}

@Database(
    entities = [SymbolsEntity::class, RateEntity::class, FavoritePairEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
internal abstract class RateTrackerRoomDatabase : RoomDatabase() {
    abstract fun rateTrackerDao(): RateTrackerDao
}