package com.manyacov.data.rate_tracker.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "symbols_table")
data class SymbolsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "symbols")
    val symbols: String,
    @ColumnInfo(name = "lastUpdate")
    val lastUpdate: Date
)
