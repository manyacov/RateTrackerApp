package com.manyacov.data.rate_tracker.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

fun RateTrackerDatabase(applicationContext: Context): RateTrackerDatabase {
    val rateTrackerDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        RateTrackerRoomDatabase::class.java,
        "rate_tracker_db"
    ).build()
    return RateTrackerDatabase(rateTrackerDatabase)
}