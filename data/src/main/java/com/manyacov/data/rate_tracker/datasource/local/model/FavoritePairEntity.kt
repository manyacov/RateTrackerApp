package com.manyacov.data.rate_tracker.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "favorites")
data class FavoritePairEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "base_symbols")
    val baseSymbols: String?,
    @ColumnInfo(name = "symbols")
    val symbols: String?,
    @ColumnInfo(name = "lastUpdate")
    val lastUpdate: Date
)
